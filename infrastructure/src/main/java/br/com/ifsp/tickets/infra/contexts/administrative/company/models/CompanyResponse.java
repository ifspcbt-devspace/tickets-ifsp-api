package br.com.ifsp.tickets.infra.contexts.administrative.company.models;

import br.com.ifsp.tickets.infra.shared.address.AddressResponse;
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

}