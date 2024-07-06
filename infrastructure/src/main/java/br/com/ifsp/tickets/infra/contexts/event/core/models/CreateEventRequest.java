package br.com.ifsp.tickets.infra.contexts.event.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;

public record CreateEventRequest(
        @JsonProperty("company_id") String companyId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("init_date") Date initDate,
        @JsonProperty("end_date") Date endDate,
        @JsonProperty("configuration") HashMap<String, String> configuration
) {
}
