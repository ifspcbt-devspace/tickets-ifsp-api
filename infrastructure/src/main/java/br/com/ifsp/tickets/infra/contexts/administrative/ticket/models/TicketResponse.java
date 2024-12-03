package br.com.ifsp.tickets.infra.contexts.administrative.ticket.models;

import br.com.ifsp.tickets.domain.administrative.ticket.TicketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("user_id")
        String userId,
        @JsonProperty("document")
        String document,
        @JsonProperty("event_id")
        String eventId,
        @JsonProperty("description")
        String description,
        @JsonProperty("valid_in")
        LocalDate validIn,
        @JsonProperty("expired_in")
        LocalDate expiredIn,
        @JsonProperty("status")
        TicketStatus status,
        @JsonProperty("code")
        String code,
        @JsonProperty("last_time_consumed")
        LocalDateTime lastTimeConsumed
) {
}
