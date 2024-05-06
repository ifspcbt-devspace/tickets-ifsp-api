package br.com.ifsp.tickets.app.auth.get;

import br.com.ifsp.tickets.domain.user.User;

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
