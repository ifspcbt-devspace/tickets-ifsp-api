package br.com.ifsp.tickets.app.company.update;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.shared.vo.Address;

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

    public record AddressOutput(
            String street,
            String complement,
            String number,
            String neighborhood,
            String city,
            String state,
            String country,
            String zipCode
    ) {

        public static AddressOutput from(Address address) {
            return new AddressOutput(
                    address.getStreet(),
                    address.getComplement(),
                    address.getNumber(),
                    address.getNeighborhood(),
                    address.getCity(),
                    address.getState(),
                    address.getCountry(),
                    address.getZipCode()
            );
        }

    }
}
