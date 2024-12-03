package br.com.ifsp.tickets.app.administrative.company.update;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record UpdateCompanyInput(
        String id,
        String name,
        String description,
        String cnpj,
        User authenticatedUser
) {

    public UpdateCompanyInput {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("field 'id' cannot be null or empty");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("field 'name' cannot be null or empty");

        if (cnpj == null || cnpj.isBlank())
            throw new IllegalArgumentException("field 'cnpj' cannot be null or empty");

    }

    public static UpdateCompanyInput of(String id, String name, String description, String cnpj, User authenticatedUser) {
        return new UpdateCompanyInput(id, name, description, cnpj, authenticatedUser);
    }

}
