package br.com.ifsp.tickets.app.administrative.ticket.check;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record CheckTicketInput(
        User user,
        String id
) {
    public static CheckTicketInput of(User user, String id) {
        return new CheckTicketInput(user, id);
    }
}
