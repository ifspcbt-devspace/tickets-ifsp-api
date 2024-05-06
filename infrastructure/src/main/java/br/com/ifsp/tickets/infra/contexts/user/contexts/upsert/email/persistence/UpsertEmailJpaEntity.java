package br.com.ifsp.tickets.infra.contexts.user.contexts.upsert.email.persistence;

import br.com.ifsp.tickets.domain.user.email.UpsertEmail;
import br.com.ifsp.tickets.domain.user.email.UpsertEmailID;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "upsert_emails")
@NoArgsConstructor
@Getter
public class UpsertEmailJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "request_date", nullable = false, updatable = false)
    private LocalDateTime requestDate;
    @Column(name = "token", nullable = false, unique = true, updatable = false)
    private String token;
    @Column(name = "last_notification_date")
    private LocalDateTime lastNotificationDate;

    public UpsertEmailJpaEntity(UUID id, UserJpaEntity user, String email, LocalDateTime requestDate, String token, LocalDateTime lastNotificationDate) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.requestDate = requestDate;
        this.token = token;
        this.lastNotificationDate = lastNotificationDate;
    }

    public static UpsertEmailJpaEntity from(UpsertEmail upsertEmail) {
        return new UpsertEmailJpaEntity(
                upsertEmail.getId().getValue(),
                UserJpaEntity.from(upsertEmail.getUser()),
                upsertEmail.getEmail().getValue(),
                upsertEmail.getRequestDate(),
                upsertEmail.getToken(),
                upsertEmail.getLastNotificationDate()
        );
    }

    public UpsertEmail toAggregate() {
        return UpsertEmail.with(
                new UpsertEmailID(this.id),
                new EmailAddress(this.email),
                this.requestDate,
                this.user.toAggregate(),
                this.token,
                this.lastNotificationDate
        );
    }

}
