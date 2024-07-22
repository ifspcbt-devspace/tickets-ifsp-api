package br.com.ifsp.tickets.app.ticket.check;

import br.com.ifsp.tickets.domain.user.User;

public record CheckTicketInput(
        User user,
        String id
) {
    public static CheckTicketInput of(User user, String id) {
        return new CheckTicketInput(user, id);
    }
}
