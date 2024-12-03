package br.com.ifsp.tickets.app.administrative.company.retrieve.search;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.administrative.company.Company;

public record SearchCompanyOutput(
        String id,
        String name,
        String description,
        String cnpj,
        AddressOutput address
) {

    public static SearchCompanyOutput from(Company company) {
        return new SearchCompanyOutput(
                company.getId().getValue().toString(),
                company.getName(),
                company.getDescription(),
                company.getCnpj().getValue(),
                AddressOutput.from(company.getAddress())
        );
    }
}
