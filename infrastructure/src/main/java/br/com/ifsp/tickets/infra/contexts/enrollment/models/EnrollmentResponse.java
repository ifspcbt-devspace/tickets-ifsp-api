package br.com.ifsp.tickets.infra.contexts.enrollment.models;

import br.com.ifsp.tickets.domain.enrollment.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnrollmentResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("user_id")
        String userID,
        @JsonProperty("event_id")
        String eventID,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("status")
        EnrollmentStatus status,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
}
