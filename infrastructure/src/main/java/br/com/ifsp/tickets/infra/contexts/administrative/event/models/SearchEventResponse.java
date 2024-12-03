package br.com.ifsp.tickets.infra.contexts.administrative.event.models;

import br.com.ifsp.tickets.domain.administrative.event.EventStatus;
import br.com.ifsp.tickets.infra.shared.address.AddressResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.HashMap;

public record SearchEventResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("company_id")
        String companyId,
        @JsonProperty("init_date")
        LocalDate initDate,
        @JsonProperty("end_date")
        LocalDate endDate,
        @JsonProperty("status")
        EventStatus status,
        @JsonProperty("address")
        AddressResponse address,
        @JsonProperty("configuration")
        HashMap<String, String> configuration
) {
}
