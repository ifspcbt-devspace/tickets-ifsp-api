package br.com.ifsp.tickets.app.shared;

import br.com.ifsp.tickets.domain.shared.vo.Address;

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
