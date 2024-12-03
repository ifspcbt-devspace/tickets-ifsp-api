package br.com.ifsp.tickets.domain.shared.zipcode;

public record ZipCode(
        String value,
        String street,
        String neighborhood,
        String city,
        String state
) {

    public ZipCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ZipCode cannot be null or empty");
        }
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (neighborhood == null || neighborhood.isBlank()) {
            throw new IllegalArgumentException("Neighborhood cannot be null or empty");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or empty");
        }
    }

}
