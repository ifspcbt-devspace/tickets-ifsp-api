package br.com.ifsp.tickets.infra.contexts.company.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCompanyRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cnpj") String cnpj
) {
}
