package br.com.ifsp.tickets.app.financial.product.delete;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record DeleteTicket2SellInput(
        User user,
        String ticketId
) {
}
