package br.com.ifsp.tickets.domain.event.sale;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketSale extends AggregateRoot<TicketSaleID> {

    private final EventID eventID;
    private String name; // example: Ingresso Unitário + Meia entrada
    private String description; // example: válido para uma pessoa e uma criança de até 12 anos
    private BigDecimal price; // example: 20.00
    private int entries; // example: 2
    private boolean active;

    public TicketSale(TicketSaleID ticketSaleID, EventID eventID, String name, String description, BigDecimal price, int entries, boolean active) {
        super(ticketSaleID);
        this.eventID = eventID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.entries = entries;
        this.active = active;
    }

    public static TicketSale with(TicketSaleID ticketSaleID, EventID eventID, String name, String description, BigDecimal price, int entries, boolean active) {
        return new TicketSale(ticketSaleID, eventID, name, description, price, entries, active);
    }

    public static TicketSale newTicketSale(Event event, String name, String description, BigDecimal price, int entries) {
        return new TicketSale(TicketSaleID.unique(), event.getId(), name, description, price, entries, true);
    }

    public void update(String name, String description, BigDecimal price, int entries) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.entries = entries;
    }

    public void toggle() {
        this.active = !this.active;
    }

    @Override
    public void validate(IValidationHandler handler) {
        new TicketSaleValidator(handler, this).validate();
    }
}
