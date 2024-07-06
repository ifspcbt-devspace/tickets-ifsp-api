package br.com.ifsp.tickets.app.event.sale.delete;

import br.com.ifsp.tickets.domain.user.User;

public record DeleteTicket2SellInput(
        User user,
        String ticketId
) {
}
