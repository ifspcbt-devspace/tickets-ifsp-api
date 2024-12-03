package br.com.ifsp.tickets.app.financial.order.retrieve.get;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record GetOrderByIDInput(
        User authenticatedUser,
        String document,
        Long id
) {

    public static GetOrderByIDInput of(User authenticatedUser, String document, Long id) {
        return new GetOrderByIDInput(authenticatedUser, document, id);
    }

}
