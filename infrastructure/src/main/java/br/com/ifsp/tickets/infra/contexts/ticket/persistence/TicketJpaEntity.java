package br.com.ifsp.tickets.infra.contexts.ticket.persistence;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.ticket.TicketStatus;
import br.com.ifsp.tickets.domain.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.user.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Getter
public class TicketJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "event_id", nullable = false)
    private UUID eventId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "valid_in", nullable = false)
    private Date validIn;
    @Column(name = "expired_in", nullable = false)
    private Date expiredIn;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "last_time_consumed")
    private LocalDateTime lastTimeConsumed;

    public TicketJpaEntity(UUID id, UUID userId, UUID eventId, String description, String status, String code, Date validIn, Date expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.description = description;
        this.status = status;
        this.code = code;
        this.validIn = validIn;
        this.expiredIn = expiredIn;
        this.createdAt = createdAt;
        this.lastTimeConsumed = lastTimeConsumed;
    }

    public static TicketJpaEntity from(Ticket ticket) {
        return new TicketJpaEntity(
                ticket.getId().getValue(),
                ticket.getUserID().getValue(),
                ticket.getEventID().getValue(),
                ticket.getDescription(),
                ticket.getStatus().getDescription(),
                ticket.getCode().getCode(),
                ticket.getValidIn(),
                ticket.getExpiredIn(),
                ticket.getCreatedAt(),
                ticket.getLastTimeConsumed()
        );
    }

    public Ticket toAggregate() {
        return Ticket.with(
                TicketID.with(this.id),
                UserID.with(this.userId),
                EventID.with(this.eventId),
                this.description,
                TicketStatus.valueOf(this.status),
                new TicketCode(this.code),
                this.validIn,
                this.expiredIn,
                this.createdAt,
                this.lastTimeConsumed
        );
    }
}
