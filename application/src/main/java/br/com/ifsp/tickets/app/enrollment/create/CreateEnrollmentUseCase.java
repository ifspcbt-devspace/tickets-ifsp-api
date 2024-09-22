package br.com.ifsp.tickets.app.enrollment.create;

import br.com.ifsp.tickets.app.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.user.User;

import java.time.LocalDate;

public class CreateEnrollmentUseCase implements ICreateEnrollmentUseCase {

    private final IEventGateway eventGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final ITicketGateway ticketGateway;
    private final IMessageGateway messageGateway;
    private final ICompanyGateway companyGateway;
    private final IEmailGateway emailGateway;
    private final IFileStorage fileProvider;
    private final ITicketQRGenerator ticketGenerator;

    public CreateEnrollmentUseCase(IEventGateway eventGateway, IEnrollmentGateway enrollmentGateway, ITicketGateway ticketGateway, IMessageGateway messageGateway, ICompanyGateway companyGateway, IEmailGateway emailGateway, IFileStorage fileProvider, ITicketQRGenerator ticketGenerator) {
        this.eventGateway = eventGateway;
        this.enrollmentGateway = enrollmentGateway;
        this.ticketGateway = ticketGateway;
        this.messageGateway = messageGateway;
        this.companyGateway = companyGateway;
        this.emailGateway = emailGateway;
        this.fileProvider = fileProvider;
        this.ticketGenerator = ticketGenerator;
    }

    @Override
    public CreateEnrollmentOutput execute(CreateEnrollmentInput anIn) {
        final User user = anIn.user();
        final EventID eventID = EventID.with(anIn.eventId());
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        final Company company = this.companyGateway.findById(event.getCompanyID()).orElseThrow(() -> NotFoundException.with(Company.class, event.getCompanyID()));
        if (this.enrollmentGateway.existsByUserIDAndEventID(user.getId(), eventID)) {
            Notification.create("Validation Error").append("User already enrolled in this event").throwPossibleErrors();
        }
        final Enrollment enrollment = Enrollment.newEnrollment(user.getId(), event.getId());

        final LocalDate expiredIn = event.getEndDate().plusDays(1);
        final Ticket ticket = Ticket.newTicket(user.getId(), event, "Ingresso sem limite de acompanhantes", event.getInitDate(), expiredIn);
        final Message message = this.messageGateway.findBySubjectAndType(MessageSubject.EVENT_TICKET, MessageType.HTML).orElseThrow(() -> NotFoundException.with("Email template not found"));
        final Notification notification = Notification.create();
        enrollment.validate(notification);
        ticket.validate(notification);
        notification.throwPossibleErrors();
        final Enrollment createdEnrollment = this.enrollmentGateway.create(enrollment);
        final Ticket createdTicket = this.ticketGateway.create(ticket);

        final Email email = Email.createDynamic(user.getEmail().toString(), message, user.getName(), company.getName());
        email.appendAttachment("qr-code.png", ticketGenerator.generateQRCodeToBase64(createdTicket.getId()), fileProvider);
        email.validate(notification);
        notification.throwPossibleErrors();

        final Email createdEmail = this.emailGateway.create(email);
        return CreateEnrollmentOutput.from(createdEnrollment, createdTicket, createdEmail);
    }
}
