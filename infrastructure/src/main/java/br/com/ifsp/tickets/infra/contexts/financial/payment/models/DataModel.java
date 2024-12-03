package br.com.ifsp.tickets.infra.contexts.financial.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataModel(
        @JsonProperty("id") Long id
) {
}
