package br.com.ifsp.tickets.infra.contexts.enrollment.upsert.models;

import br.com.ifsp.tickets.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CreateUpsertEnrollmentRequest(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("document") String document,
        @JsonProperty("birth_date") LocalDate birthDate,
        @JsonProperty("ticket_sale_id") String ticketSaleId
) {
}
