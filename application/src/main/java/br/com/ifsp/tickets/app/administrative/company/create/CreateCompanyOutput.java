package br.com.ifsp.tickets.app.administrative.company.create;

import br.com.ifsp.tickets.domain.administrative.company.Company;

public record CreateCompanyOutput(
        String id
) {

    public static CreateCompanyOutput from(Company company) {
        return new CreateCompanyOutput(company.getId().getValue().toString());
    }

}
