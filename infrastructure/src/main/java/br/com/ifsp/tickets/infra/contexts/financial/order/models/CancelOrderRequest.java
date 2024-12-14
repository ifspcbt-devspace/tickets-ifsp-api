package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelOrderRequest(
        @JsonProperty("document")
        String document
) {
}
