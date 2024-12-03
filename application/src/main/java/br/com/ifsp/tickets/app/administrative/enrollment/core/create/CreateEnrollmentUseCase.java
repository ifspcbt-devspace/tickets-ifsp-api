package br.com.ifsp.tickets.app.administrative.enrollment.core.create;

import br.com.ifsp.tickets.app.administrative.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.administrative.company.Company;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.administrative.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.event.EventStatus;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

import java.time.LocalDate;

public class CreateEnrollmentUseCase implements ICreateEnrollmentUseCase {

    private final IEventGateway eventGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final ITicketGateway ticketGateway;
    private final ITicketSaleGateway ticketSaleGateway;
    private final IMessageGateway messageGateway;
    private final ICompanyGateway companyGateway;
    private final IEmailGateway emailGateway;
    private final IFileStorage fileProvider;
    private final ITicketQRGenerator ticketGenerator;

    public CreateEnrollmentUseCase(IEventGateway eventGateway, IEnrollmentGateway enrollmentGateway, ITicketGateway ticketGateway, ITicketSaleGateway ticketSaleGateway, IMessageGateway messageGateway, ICompanyGateway companyGateway, IEmailGateway emailGateway, IFileStorage fileProvider, ITicketQRGenerator ticketGenerator) {
        this.eventGateway = eventGateway;
        this.enrollmentGateway = enrollmentGateway;
        this.ticketGateway = ticketGateway;
        this.ticketSaleGateway = ticketSaleGateway;
        this.messageGateway = messageGateway;
        this.companyGateway = companyGateway;
        this.emailGateway = emailGateway;
        this.fileProvider = fileProvider;
        this.ticketGenerator = ticketGenerator;
    }

    @Override
    public CreateEnrollmentOutput execute(CreateEnrollmentInput anIn) {
        final UserID userID = UserID.with(anIn.userId());
        final String name = anIn.name();
        final String emailString = anIn.email();
        final LocalDate birthDate = anIn.birthDate();
        final String document = anIn.document();
        final TicketID ticketID = TicketID.with(anIn.ticketId());

        final EventID eventID = EventID.with(anIn.eventId());
        final TicketSaleID ticketSaleID = TicketSaleID.with(anIn.ticketSaleId());
        final TicketSale ticketSale = this.ticketSaleGateway.findById(ticketSaleID).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticketSaleID));
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        final Company company = this.companyGateway.findById(event.getCompanyID()).orElseThrow(() -> NotFoundException.with(Company.class, event.getCompanyID()));

        if (!ticketSale.getEventID().equals(eventID))
            Notification.create("Validation Error").append("Ticket sale is not for this event").throwAnyErrors();

        if (ticketSale.getPrice().doubleValue() > 0)
            Notification.create("Validation Error").append("Ticket sale is not free").throwAnyErrors();

        if (!event.getStatus().equals(EventStatus.OPENED))
            Notification.create("Event is not opened").append("Event is not open for enrollment").throwAnyErrors();

        final boolean alreadyExists = this.enrollmentGateway.existsByEmailAndEventID(emailString, eventID);

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
        final Enrollment createdEnrollment = this.enrollmentGateway.create(enrollment);
        final Ticket createdTicket = this.ticketGateway.create(ticket);

        final Email email = Email.createDynamic(emailString, message, name, company.getName(), "");
        email.appendAttachment("qr-code.png", ticketGenerator.generateQRCodeToBase64(createdTicket.getId()), fileProvider);
        email.validate(notification);
        notification.throwAnyErrors();

        final Email createdEmail = this.emailGateway.create(email);
        return CreateEnrollmentOutput.from(createdEnrollment, createdTicket, createdEmail);
    }
}
