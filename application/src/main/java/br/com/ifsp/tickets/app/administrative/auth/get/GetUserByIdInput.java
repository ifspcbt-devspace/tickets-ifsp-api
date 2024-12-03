package br.com.ifsp.tickets.app.administrative.auth.get;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record GetUserByIdInput(
        String id,
        User authenticatedUser
) {

    public GetUserByIdInput {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("field 'id' cannot be null or empty");
    }

    public static GetUserByIdInput of(String id, User authenticatedUser) {
        return new GetUserByIdInput(id, authenticatedUser);
    }

}
