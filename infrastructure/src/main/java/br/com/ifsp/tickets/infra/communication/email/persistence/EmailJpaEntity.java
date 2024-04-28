package br.com.ifsp.tickets.infra.communication.email.persistence;

import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.EmailID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "emails")
@NoArgsConstructor
@Getter
public class EmailJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "target", nullable = false)
    private String target;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "body", columnDefinition = "TEXT", nullable = false)
    private String body;
    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts;
    @ElementCollection
    @CollectionTable(name = "email_attachments", joinColumns = @JoinColumn(name = "email_id"))
    private List<String> attachments;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "sent", nullable = false)
    private boolean sent;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public EmailJpaEntity(Long id, String target, String subject, String body, int failedAttempts, List<String> attachments, LocalDateTime createdAt, boolean sent, LocalDateTime sentAt) {
        this.id = id;
        this.target = target;
        this.subject = subject;
        this.body = body;
        this.failedAttempts = failedAttempts;
        this.attachments = attachments;
        this.createdAt = createdAt;
        this.sent = sent;
        this.sentAt = sentAt;
    }

    public static EmailJpaEntity from(Email email) {
        return new EmailJpaEntity(
                email.getId().getValue(),
                email.getTarget(),
                email.getSubject(),
                email.getBody(),
                email.getFailedAttempts(),
                email.getAttachments(),
                email.getCreatedAt(),
                email.isSent(),
                email.getSentAt()
        );
    }

    public Email toAggregate() {
        return new Email(
                new EmailID(this.id),
                this.getTarget(),
                this.getSubject(),
                this.getBody(),
                this.getAttachments(),
                this.getCreatedAt(),
                this.failedAttempts,
                this.isSent(),
                this.getSentAt()
        );
    }
}
