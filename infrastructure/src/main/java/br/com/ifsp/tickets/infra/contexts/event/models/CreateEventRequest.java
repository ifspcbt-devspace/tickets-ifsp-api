package br.com.ifsp.tickets.infra.contexts.event.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;

public record CreateEventRequest(
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("init_date")
        Date initDate,
        @JsonProperty("end_date")
        Date endDate,
        @JsonProperty("configuration")
        HashMap<String, String> configuration
) {
}
