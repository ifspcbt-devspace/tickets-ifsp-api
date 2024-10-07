package br.com.ifsp.tickets.infra.contexts.ticket.persistence;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.ticket.TicketStatus;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;
import br.com.ifsp.tickets.domain.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.infra.shared.encryption.EncryptionService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "document", nullable = false)
    private String document;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "event_id", nullable = false)
    private UUID eventId;
    @Column(name = "ticket_sale_id", nullable = false)
    private UUID ticketSaleId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;
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

    public TicketJpaEntity(UUID id, String document, UUID userId, UUID eventId, UUID ticketSaleId, String description, String status, String paymentStatus, String code, LocalDate validIn, LocalDate expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed) {
        this.id = id;
        this.document = EncryptionService.encrypt(document);
        this.userId = userId;
        this.ticketSaleId = ticketSaleId;
        this.description = description;
        this.status = status;
        this.paymentStatus = paymentStatus;
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
                ticket.getDocument(),
                ticket.getUserID().isPresent() ? ticket.getUserID().get().getValue() : null,
                ticket.getEventID().getValue(),
                ticket.getTicketSaleID().getValue(),
                ticket.getDescription(),
                ticket.getStatus().name(),
                ticket.getPaymentStatus().name(),
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
                this.getDecryptedDocument(),
                EventID.with(this.eventId),
                TicketSaleID.with(this.ticketSaleId),
                this.description,
                TicketStatus.valueOf(this.status),
                PaymentStatus.valueOf(this.paymentStatus),
                new TicketCode(this.code),
                this.validIn,
                this.expiredIn,
                this.createdAt,
                this.lastTimeConsumed,
                UserID.with(this.userId)
        );
    }

    public String getDecryptedDocument() {
        return EncryptionService.decrypt(this.document);
    }
}
