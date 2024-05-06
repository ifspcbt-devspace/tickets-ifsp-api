package br.com.ifsp.tickets.infra.contexts.company.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("cnpj")
        String cnpj,
        @JsonProperty("address")
        AddressResponse address
) {
    public record AddressResponse(
            @JsonProperty("street")
            String street,
            @JsonProperty("number")
            String number,
            @JsonProperty("complement")
            String complement,
            @JsonProperty("neighborhood")
            String neighborhood,
            @JsonProperty("city")
            String city,
            @JsonProperty("state")
            String state,
            @JsonProperty("country")
            String country,
            @JsonProperty("zip_code")
            String zipCode
    ) {
    }
}