package br.com.ifsp.tickets.domain.shared.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalAddressException;
import lombok.Getter;

@Getter
public class Address extends ValueObject {

    private final String street;
    private final String number;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String country;
    private final String zipCode;

    public Address(String street, String number, String neighborhood, String city, String state, String country, String zipCode) {
        if (street == null || street.isBlank())
            throw new IllegalAddressException("Street is required");

        if (number == null || number.isBlank())
            throw new IllegalAddressException("Number is required");

        if (neighborhood == null || neighborhood.isBlank())
            throw new IllegalAddressException("Neighborhood is required");

        if (city == null || city.isBlank())
            throw new IllegalAddressException("City is required");

        if (state == null || state.isBlank())
            throw new IllegalAddressException("State is required");

        if (country == null || country.isBlank())
            throw new IllegalAddressException("Country is required");

        if (zipCode == null || zipCode.isBlank())
            throw new IllegalAddressException("ZipCode is required");

        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!street.equals(address.street)) return false;
        if (!number.equals(address.number)) return false;
        if (!neighborhood.equals(address.neighborhood)) return false;
        if (!city.equals(address.city)) return false;
        if (!state.equals(address.state)) return false;
        if (!country.equals(address.country)) return false;
        return zipCode.equals(address.zipCode);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + neighborhood.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + zipCode.hashCode();
        return result;
    }
}
