package br.com.ifsp.tickets.app.financial.product.create;

import br.com.ifsp.tickets.domain.financial.product.TicketSale;

public record CreateTicket2SellOutput(
        String id
) {

    public static CreateTicket2SellOutput from(TicketSale ticketSale) {
        return new CreateTicket2SellOutput(ticketSale.getId().toString());
    }

}
