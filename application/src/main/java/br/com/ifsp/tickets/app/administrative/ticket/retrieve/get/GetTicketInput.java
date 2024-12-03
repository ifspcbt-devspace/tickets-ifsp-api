package br.com.ifsp.tickets.app.administrative.ticket.retrieve.get;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record GetTicketInput(
        User user,
        String ticketID
) {

    public static GetTicketInput of(User user, String ticketID) {
        return new GetTicketInput(user, ticketID);
    }
}
