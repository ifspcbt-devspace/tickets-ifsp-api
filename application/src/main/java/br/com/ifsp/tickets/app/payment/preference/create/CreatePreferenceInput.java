package br.com.ifsp.tickets.app.payment.preference.create;

import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;

public record CreatePreferenceInput(
        User user,
        String ticketId,
        String ticket_sale_id
) {

    public static CreatePreferenceInput of(final User user, String ticket_sale_id) {
        return new CreatePreferenceInput(user, TicketID.unique().getValue().toString(), ticket_sale_id);
    }
}
