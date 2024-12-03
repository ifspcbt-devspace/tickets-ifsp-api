package br.com.ifsp.tickets.app.financial.order.cancel;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record CancelOrderInput(
        User authenticatedUser,
        String document,
        Long id
) {

    public static CancelOrderInput of(User authenticatedUser, String document, Long id) {
        return new CancelOrderInput(authenticatedUser, document, id);
    }
}
