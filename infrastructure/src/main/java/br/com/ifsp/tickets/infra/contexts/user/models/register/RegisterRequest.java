package br.com.ifsp.tickets.infra.contexts.user.models.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record RegisterRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("birth_date") String birthDate,
        @JsonProperty("document") String document,
        @JsonProperty("phone_number") String phoneNumber
) {

    public LocalDate getBirthDate() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(birthDate, formatter);
    }
}
