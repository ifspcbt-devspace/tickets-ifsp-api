package br.com.ifsp.tickets.app.company.create;

public record CreateCompanyInput(
        String ownerId,
        String name,
        String description,
        String cnpj,
        String street,
        String complement,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode
) {

    public CreateCompanyInput {
        if (ownerId == null || ownerId.isBlank()) {
            throw new IllegalArgumentException("field 'owner_id' cannot be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("field 'name' cannot be null or empty");
        }
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("field 'cnpj' cannot be null or empty");
        }
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("field 'street' cannot be null or empty");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("field 'number' cannot be null or empty");
        }
        if (neighborhood == null || neighborhood.isBlank()) {
            throw new IllegalArgumentException("field 'neighborhood' cannot be null or empty");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("field 'city' cannot be null or empty");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("field 'state' cannot be null or empty");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("field 'zip_code' cannot be null or empty");
        }

        zipCode = zipCode.replaceAll("[^0-9]", "");
    }

    public static CreateCompanyInput of(String ownerId, String name, String description, String cnpj, String street, String complement, String number, String neighborhood, String city, String state, String zipCode) {
        return new CreateCompanyInput(ownerId, name, description, cnpj, street, complement, number, neighborhood, city, state, zipCode);
    }

}
