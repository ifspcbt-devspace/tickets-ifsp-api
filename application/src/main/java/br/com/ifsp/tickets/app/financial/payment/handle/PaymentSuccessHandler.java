package br.com.ifsp.tickets.app.financial.payment.handle;

import br.com.ifsp.tickets.app.administrative.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.administrative.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.event.OrderPaymentSuccess;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;

import java.time.LocalDate;

public class PaymentSuccessHandler {

    private final IMessageGateway messageGateway;
    private final IEventGateway eventGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final ICompanyGateway companyGateway;
    private final ITicketGateway ticketGateway;
    private final IEmailGateway emailGateway;
    private final IFileStorage fileProvider;
    private final ITicketQRGenerator ticketGenerator;
    private final IOrderGateway orderGateway;
    private final IDomainEventPublisher eventPublisher;

    public PaymentSuccessHandler(IMessageGateway messageGateway, IEventGateway eventGateway, IEnrollmentGateway enrollmentGateway, ICompanyGateway companyGateway, ITicketGateway ticketGateway, IEmailGateway emailGateway, IFileStorage fileProvider, ITicketQRGenerator ticketGenerator, IOrderGateway orderGateway, IDomainEventPublisher eventPublisher) {
        this.messageGateway = messageGateway;
        this.eventGateway = eventGateway;
        this.enrollmentGateway = enrollmentGateway;
        this.companyGateway = companyGateway;
        this.ticketGateway = ticketGateway;
        this.emailGateway = emailGateway;
        this.fileProvider = fileProvider;
        this.ticketGenerator = ticketGenerator;
        this.orderGateway = orderGateway;
        this.eventPublisher = eventPublisher;
    }

    public void handle(Order order) {
        order.approve();
        order = this.orderGateway.update(order);
        order.registerEvent(new OrderPaymentSuccess(order));
        order.publishDomainEvents(this.eventPublisher);

        for (OrderItem item : order.getItems()) {
            final TicketSale ticketSale = item.getTicket();
            final Event event = this.eventGateway.findById(ticketSale.getEventID()).orElseThrow(() -> NotFoundException.with(Event.class, ticketSale.getEventID()));
            final Company company = this.companyGateway.findById(event.getCompanyID()).orElseThrow(() -> NotFoundException.with(Company.class, event.getCompanyID()));
            final String emailString = order.getEmail().toString();
            final String name = order.getName();
            final String document = order.getDocument().toString();
            final LocalDate birthDate = order.getBirthDate();
            final UserID userID = order.getCustomer().map(User::getId).orElse(new UserID(null));

            final boolean alreadyExists = this.enrollmentGateway.existsByEmailAndEventID(emailString, event.getId());

            if (alreadyExists) {
                Notification.create("Validation Error").append("User already enrolled in this event").throwAnyErrors();
            }

            final Enrollment enrollment = Enrollment
                    .newEnrollment(name, emailString, document, birthDate,
                            userID, event.getId());

            final LocalDate expiredIn = event.getEndDate().plusDays(1);

            final Ticket ticket = Ticket.newTicket(enrollment, event, ticketSale, ticketSale.getDescription(), event.getInitDate(), expiredIn);

            final Message message = this.messageGateway.findBySubjectAndType(MessageSubject.EVENT_TICKET, MessageType.HTML).orElseThrow(() -> NotFoundException.with("Email template not found"));

            final Notification notification = Notification.create("An error occurred while validating the enrollment");
            enrollment.validate(notification);
            ticket.validate(notification);
            notification.throwAnyErrors();

            this.enrollmentGateway.create(enrollment);
            final Ticket createdTicket = this.ticketGateway.create(ticket);

            final Email email = Email.createDynamic(emailString, message, name, company.getName(), "");

            email.appendAttachment("qr-code.png", ticketGenerator.generateQRCodeToBase64(createdTicket.getId()), fileProvider);
            email.validate(notification);
            notification.throwAnyErrors();

            this.emailGateway.create(email);
        }
    }
}
