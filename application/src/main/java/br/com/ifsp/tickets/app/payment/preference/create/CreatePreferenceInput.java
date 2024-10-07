package br.com.ifsp.tickets.app.payment.preference.create;

import br.com.ifsp.tickets.domain.user.User;

public record CreatePreferenceInput(
        User user,
        String ticketId
) {

    public static CreatePreferenceInput of(final User user, final String ticketId) {
        return new CreatePreferenceInput(user, ticketId);
    }
}
