package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetOrderRequest(
        @JsonProperty("document")
        String document
) {
}
