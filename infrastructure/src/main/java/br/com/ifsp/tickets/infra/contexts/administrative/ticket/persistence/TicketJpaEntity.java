package br.com.ifsp.tickets.infra.contexts.administrative.ticket.persistence;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketStatus;
import br.com.ifsp.tickets.domain.administrative.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.persistence.EnrollmentJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Getter
public class TicketJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "enrollment_id", referencedColumnName = "id", nullable = false)
    private EnrollmentJpaEntity enrollment;
    @Column(name = "event_id", nullable = false)
    private UUID eventId;
    @Column(name = "ticket_sale_id", nullable = false)
    private UUID ticketSaleId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "valid_in", nullable = false)
    private LocalDate validIn;
    @Column(name = "expired_in", nullable = false)
    private LocalDate expiredIn;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "last_time_consumed")
    private LocalDateTime lastTimeConsumed;

    public TicketJpaEntity(UUID id, EnrollmentJpaEntity enrollment, UUID eventId, UUID ticketSaleId, String description, String status, String code, LocalDate validIn, LocalDate expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed) {
        this.id = id;
        this.enrollment = enrollment;
        this.ticketSaleId = ticketSaleId;
        this.description = description;
        this.status = status;
        this.code = code;
        this.eventId = eventId;
        this.validIn = validIn;
        this.expiredIn = expiredIn;
        this.createdAt = createdAt;
        this.lastTimeConsumed = lastTimeConsumed;
    }

    public static TicketJpaEntity from(Ticket ticket) {
        return new TicketJpaEntity(
                ticket.getId().getValue(),
                EnrollmentJpaEntity.from(ticket.getEnrollment()),
                ticket.getEventID().getValue(),
                ticket.getTicketSaleID().getValue(),
                ticket.getDescription(),
                ticket.getStatus().name(),
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
                this.enrollment.toAggregate(),
                EventID.with(this.eventId),
                TicketSaleID.with(this.ticketSaleId),
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
