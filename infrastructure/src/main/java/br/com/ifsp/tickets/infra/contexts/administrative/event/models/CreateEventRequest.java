package br.com.ifsp.tickets.infra.contexts.administrative.event.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.HashMap;

public record CreateEventRequest(
        @JsonProperty("company_id") String companyId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("init_date") LocalDate initDate,
        @JsonProperty("end_date") LocalDate endDate,
        @JsonProperty("configuration") HashMap<String, String> configuration
) {
}
