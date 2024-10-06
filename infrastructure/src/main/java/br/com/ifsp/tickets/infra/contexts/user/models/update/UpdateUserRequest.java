package br.com.ifsp.tickets.infra.contexts.user.models.update;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record UpdateUserRequest(
        @JsonProperty("name")
        String name,
        @JsonProperty("bio")
        String bio,
        @JsonProperty("birth_date")
        String birthDate,
        @JsonProperty("document")
        String document
) {

    public LocalDate getBirthDate() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(birthDate, formatter);
    }

}
