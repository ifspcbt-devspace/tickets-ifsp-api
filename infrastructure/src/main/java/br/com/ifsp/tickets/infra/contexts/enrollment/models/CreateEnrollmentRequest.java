package br.com.ifsp.tickets.infra.contexts.enrollment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEnrollmentRequest(
        @JsonProperty("event_id") String eventId
) {
}
