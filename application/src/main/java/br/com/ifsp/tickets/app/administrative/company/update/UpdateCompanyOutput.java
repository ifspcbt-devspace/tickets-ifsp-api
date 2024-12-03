package br.com.ifsp.tickets.app.administrative.company.update;

import br.com.ifsp.tickets.app.shared.AddressOutput;
import br.com.ifsp.tickets.domain.administrative.company.Company;

public record UpdateCompanyOutput(
        String id,
        String name,
        String description,
        String cnpj,
        AddressOutput address
) {

    public static UpdateCompanyOutput from(Company company) {
        return new UpdateCompanyOutput(
                company.getId().getValue().toString(),
                company.getName(),
                company.getDescription(),
                company.getCnpj().getValue(),
                AddressOutput.from(company.getAddress())
        );
    }

}
