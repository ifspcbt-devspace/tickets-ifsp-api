package br.com.ifsp.tickets.infra.contexts.event.sale.ticket.persistence;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ticket_sale")
@NoArgsConstructor
@Getter
public class TicketSaleJpaEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "event_id", nullable = false)
    private UUID eventID;
    @Column(name = "name", nullable = false)
    private String name; // example: Ingresso Unitário + Meia entrada
    @Column(name = "description", nullable = false)
    private String description; // example: válido para uma pessoa e uma criança de até 12 anos
    @Column(name = "price", nullable = false)
    private BigDecimal price; // example: 20.00
    @Column(name = "entries", nullable = false)
    private int entries; // example: 2
    @Column(name = "active", nullable = false)
    private boolean active;

    public TicketSaleJpaEntity(UUID id, UUID eventID, String name, String description, BigDecimal price, int entries, boolean active) {
        this.id = id;
        this.eventID = eventID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.entries = entries;
        this.active = active;
    }

    public static TicketSaleJpaEntity from(TicketSale ticketSale) {
        return new TicketSaleJpaEntity(
                ticketSale.getId().getValue(),
                ticketSale.getEventID().getValue(),
                ticketSale.getName(),
                ticketSale.getDescription(),
                ticketSale.getPrice(),
                ticketSale.getEntries(),
                ticketSale.isActive()
        );
    }

    public TicketSale toAggregate() {
        return new TicketSale(
                TicketSaleID.with(this.getId()),
                EventID.with(this.getEventID()),
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.getEntries(),
                this.isActive()
        );
    }
}
