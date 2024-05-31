package br.com.ifsp.tickets.infra.contexts.company.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCompanyRequest(
        @JsonProperty("owner_id") String ownerId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cnpj") String cnpj,
        @JsonProperty("address") CreateAddressRequest address
) {

    public record CreateAddressRequest(
            @JsonProperty("street") String street,
            @JsonProperty("number") String number,
            @JsonProperty("complement") String complement,
            @JsonProperty("neighborhood") String neighborhood,
            @JsonProperty("city") String city,
            @JsonProperty("state") String state,
            @JsonProperty("zip_code") String zipCode
    ) {
    }
}
