package br.com.ifsp.tickets.app.event.sale.retrieve;

import br.com.ifsp.tickets.domain.event.sale.TicketSale;

import java.math.BigDecimal;

public record Ticket2SellOutput(
        String id,
        String eventId,
        String name,
        String description,
        BigDecimal price,
        int entries,
        boolean active
) {

    public static Ticket2SellOutput from(TicketSale ticketSale) {
        return new Ticket2SellOutput(
                ticketSale.getId().getValue().toString(),
                ticketSale.getEventID().getValue().toString(),
                ticketSale.getName(),
                ticketSale.getDescription(),
                ticketSale.getPrice(),
                ticketSale.getEntries(),
                ticketSale.isActive()
        );
    }
}
