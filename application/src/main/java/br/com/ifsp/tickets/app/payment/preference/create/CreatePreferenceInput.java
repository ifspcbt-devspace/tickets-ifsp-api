package br.com.ifsp.tickets.app.payment.preference.create;

import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;

public record CreatePreferenceInput(
        User user,
        String ticketId
) {

    public static CreatePreferenceInput of(final User user) {
        return new CreatePreferenceInput(user, TicketID.unique().getValue().toString());
    }
}
