package br.com.ifsp.tickets.app.enrollment.create;

import br.com.ifsp.tickets.app.shared.secret.SecretConfig;
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
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.user.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

public class CreateEnrollmentUseCase implements ICreateEnrollmentUseCase {
    private final SecretConfig secretConfig = new SecretConfig();
    private final IEventGateway eventGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final ITicketGateway ticketGateway;
    private final IMessageGateway messageGateway;
    private final ICompanyGateway companyGateway;
    private final IEmailGateway emailGateway;

    public CreateEnrollmentUseCase(IEventGateway eventGateway, IEnrollmentGateway enrollmentGateway, ITicketGateway ticketGateway, IMessageGateway messageGateway, ICompanyGateway companyGateway, IEmailGateway emailGateway) {
        this.eventGateway = eventGateway;
        this.enrollmentGateway = enrollmentGateway;
        this.ticketGateway = ticketGateway;
        this.messageGateway = messageGateway;
        this.companyGateway = companyGateway;
        this.emailGateway = emailGateway;
    }

    @Override
    public CreateEnrollmentOutput execute(CreateEnrollmentInput anIn) {
        User user = anIn.user();
        EventID eventID = EventID.with(anIn.eventId());
        Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        Company company = this.companyGateway.findById(event.getCompanyID()).orElseThrow(() -> NotFoundException.with(Company.class, event.getCompanyID()));
        System.out.println("user: ok");
        Enrollment enrollment = Enrollment.newEnrollment(user.getId(), event.getId());

        LocalDate expiredIn = event.getEndDate().plusDays(1);
        Ticket ticket = Ticket.newTicket(user.getId(), event, "Ingresso sem limite de acompanhantes", event.getInitDate(), expiredIn);
        System.out.println("ticket: ok");
        Message message = this.messageGateway.findBySubjectAndType(MessageSubject.EVENT_TICKET, MessageType.HTML).orElseThrow(() -> NotFoundException.with("Email template not found"));
        final Notification notification = Notification.create();
        System.out.println("message ok");
        enrollment.validate(notification);
        ticket.validate(notification);
        notification.throwPossibleErrors();
        System.out.println("validacao ok");
        final Enrollment createdEnrollment = this.enrollmentGateway.create(enrollment);
        final Ticket createdTicket = this.ticketGateway.create(ticket);

        String qrCodeData = secretConfig.getBASE_URL() + "/" + secretConfig.getAPI_VERSION() + "/ticket/" + createdTicket.getId() + "/check";
        Email email = Email.createDynamic(user.getEmail().toString(), message, user.getName(), company.getName(), generateQRCodeToBase64(qrCodeData));
        System.out.println("email ok");
        email.validate(notification);
        notification.throwPossibleErrors();

        final Email createdEmail = this.emailGateway.create(email);
        System.out.println("fim");
        return CreateEnrollmentOutput.from(createdEnrollment, createdTicket, createdEmail);
    }

    private String generateQRCodeToBase64(String qrCodeData) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        byte[] qrCodeBytes = outputStream.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(qrCodeBytes);

        return "data:image/png;base64," + base64Image;
    }
}
