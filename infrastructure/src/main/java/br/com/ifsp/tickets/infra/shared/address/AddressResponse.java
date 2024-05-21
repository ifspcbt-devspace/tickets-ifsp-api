package br.com.ifsp.tickets.infra.shared.address;

import com.fasterxml.jackson.annotation.JsonProperty;

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
