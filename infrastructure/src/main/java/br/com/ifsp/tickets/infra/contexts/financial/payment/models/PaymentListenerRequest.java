package br.com.ifsp.tickets.infra.contexts.financial.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record PaymentListenerRequest(
        @JsonProperty("data") DataModel data,
        @JsonProperty("date_created") LocalDateTime dateCreated,
        @JsonProperty("type") String type,
        @JsonProperty("action") String action
) {
}
