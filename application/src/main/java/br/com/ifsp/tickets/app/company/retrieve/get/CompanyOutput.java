package br.com.ifsp.tickets.app.company.retrieve.get;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.company.Company;

public record CompanyOutput(
        String id,
        String name,
        String description,
        String cnpj,
        AddressOutput address
) {

    public static CompanyOutput from(Company company) {
        return new CompanyOutput(
                company.getId().getValue().toString(),
                company.getName(),
                company.getDescription(),
                company.getCnpj().getValue(),
                AddressOutput.from(company.getAddress())
        );
    }

}
