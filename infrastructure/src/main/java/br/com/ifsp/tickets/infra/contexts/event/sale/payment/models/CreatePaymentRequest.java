package br.com.ifsp.tickets.infra.contexts.event.sale.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePaymentRequest(
        @JsonProperty("id") String id,
        @JsonProperty("date_created") LocalDateTime dateCreated,
        @JsonProperty("action") String action
) {
}
