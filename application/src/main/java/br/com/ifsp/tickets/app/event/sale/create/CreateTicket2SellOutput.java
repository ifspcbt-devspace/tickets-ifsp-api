package br.com.ifsp.tickets.app.event.sale.create;

import br.com.ifsp.tickets.domain.event.sale.TicketSale;

public record CreateTicket2SellOutput(
        String id
) {

    public static CreateTicket2SellOutput from(TicketSale ticketSale) {
        return new CreateTicket2SellOutput(ticketSale.getId().toString());
    }

}
