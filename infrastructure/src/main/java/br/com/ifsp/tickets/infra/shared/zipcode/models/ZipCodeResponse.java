package br.com.ifsp.tickets.infra.shared.zipcode.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ZipCodeResponse(
        @JsonProperty("cep")
        String cep,
        @JsonProperty("street")
        String street,
        @JsonProperty("neighborhood")
        String neighborhood,
        @JsonProperty("city")
        String city,
        @JsonProperty("state")
        String state
) {
}
