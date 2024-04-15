package br.com.ifsp.tickets.infra.user.models.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record RegisterRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("birth_date") Date birthDate,
        @JsonProperty("cpf") String cpf,
        @JsonProperty("phone_number") String phoneNumber
) {
}
