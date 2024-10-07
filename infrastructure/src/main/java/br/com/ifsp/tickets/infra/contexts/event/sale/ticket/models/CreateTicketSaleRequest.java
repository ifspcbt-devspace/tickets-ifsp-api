package br.com.ifsp.tickets.infra.contexts.event.sale.ticket.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public record CreateTicketSaleRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("entries") int entries,
        @JsonProperty("active") boolean active) {
}
