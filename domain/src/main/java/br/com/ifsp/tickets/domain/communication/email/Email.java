package br.com.ifsp.tickets.domain.communication.email;

import br.com.ifsp.tickets.domain.communication.Attachment;
import br.com.ifsp.tickets.domain.communication.PlaceHolder;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.providers.IFileProvider;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class Email extends AggregateRoot<EmailID> {

    private final String target;
    private final String subject;
    private final List<String> attachments;
    private final LocalDateTime createdAt;
    private int failedAttempts;
    private String body;
    private boolean sent;
    private LocalDateTime sentAt;

    public Email(EmailID emailID, String target, String subject, String body, List<String> attachments, LocalDateTime createdAt, int failedAttempts, boolean sent, LocalDateTime sentAt) {
        super(emailID);
        this.target = target;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments == null ? List.of() : attachments;
        this.createdAt = createdAt;
        this.failedAttempts = failedAttempts;
        this.sent = sent;
        this.sentAt = sentAt;
    }

    public static Email create(String target, Message message) {
        return new Email(new EmailID(null),
                target,
                message.getSubject().getDescription(),
                message.getTemplate(),
                null,
                LocalDateTime.now(),
                0,
                false,
                null);
    }

    public static Email createDynamic(String target, Message message, String username, String companyName, String qrData) {
        return new Email(new EmailID(null),
                target,
                message.getSubject().getDescription(),
                message.getTemplate().replace("{nome}", username).replace("{company-name}", companyName).replace("{qr-code}", qrData),
                null,
                LocalDateTime.now(),
                0,
                false,
                null);
    }

    public Email with(PlaceHolder... placeHolders) {
        for (PlaceHolder ph : placeHolders) {
            this.body = this.body.replace(ph.key(), ph.value());
        }
        return this;
    }

    public void appendAttachment(String attachment, byte[] content, IFileProvider fileProvider) {
        final String fileName = fileProvider.uploadEmailAttachment(attachment, content);
        this.attachments.add(fileName);
    }

    public void removeAttachment(String attachment, IFileProvider fileProvider) {
        this.attachments.remove(attachment);
        fileProvider.deleteEmailAttachment(attachment);
    }

    public List<Attachment> getAttachments(IFileProvider fileProvider) {
        return this.attachments.stream()
                .map(fileProvider::downloadEmailAttachment)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public void send() {
        if (this.sent) return;
        this.sent = true;
        this.sentAt = LocalDateTime.now();
    }

    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }

    public Email copyToResend() {
        return new Email(new EmailID(null),
                this.target,
                this.subject,
                this.body,
                this.attachments,
                LocalDateTime.now(),
                0, false,
                null);
    }

    @Override
    public void validate(IValidationHandler handler) {
        if (this.target == null || this.target.isBlank())
            handler.append("'target' is required");

        if (this.subject == null || this.subject.isBlank())
            handler.append("'subject' is required");

        if (this.body == null || this.body.isBlank())
            handler.append("'body' is required");
    }
}
