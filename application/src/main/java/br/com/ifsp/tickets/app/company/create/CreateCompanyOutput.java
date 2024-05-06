package br.com.ifsp.tickets.app.company.create;

import br.com.ifsp.tickets.domain.company.Company;

public record CreateCompanyOutput(
        String id
) {

    public static CreateCompanyOutput from(Company company) {
        return new CreateCompanyOutput(company.getId().getValue().toString());
    }

}
