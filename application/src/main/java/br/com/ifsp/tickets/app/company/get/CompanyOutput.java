package br.com.ifsp.tickets.app.company.get;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.shared.vo.Address;

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
