package br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CreateEnrollmentRequest(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("document") String document,
        @JsonProperty("birth_date") LocalDate birthDate,
        @JsonProperty("ticket_sale_id") String ticketSaleId
) {
}
