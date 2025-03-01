package br.com.ifsp.tickets.infra.contexts.financial.product.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TicketSaleResponse(
        @JsonProperty("id") String id,
        @JsonProperty("event_id") String eventId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("entries") int entries,
        @JsonProperty("active") boolean active
) {
}
