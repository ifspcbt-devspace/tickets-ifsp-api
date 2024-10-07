package br.com.ifsp.tickets.infra.contexts.event.sale.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataModel(
        @JsonProperty("id") String id
) {
}
