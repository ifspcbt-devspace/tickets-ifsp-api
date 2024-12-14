package br.com.ifsp.tickets.infra.contexts.financial.order.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("payment_url")
        String paymentUrl
) {
}
