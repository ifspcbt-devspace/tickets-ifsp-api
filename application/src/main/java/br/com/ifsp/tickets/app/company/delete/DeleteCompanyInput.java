package br.com.ifsp.tickets.app.company.delete;

import br.com.ifsp.tickets.domain.user.User;

public record DeleteCompanyInput(
        String id,
        User authenticatedUser
) {

    public DeleteCompanyInput {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("field 'id' cannot be null or empty");
    }

    public static DeleteCompanyInput of(String id, User authenticatedUser) {
        return new DeleteCompanyInput(id, authenticatedUser);
    }

}
