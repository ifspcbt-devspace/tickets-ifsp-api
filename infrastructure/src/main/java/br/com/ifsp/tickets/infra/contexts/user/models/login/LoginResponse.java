package br.com.ifsp.tickets.infra.contexts.user.models.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponse(
        @JsonProperty("token") String token,
        @JsonProperty("user") UserResponse user
) {

    public record UserResponse(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("username") String username,
            @JsonProperty("role") RoleResponse role,
            @JsonProperty("birth_date") String birthDate,
            @JsonProperty("cpf_initials") String cpfInitials,
            @JsonProperty("phone_number_initials") String phoneNumberInitials,
            @JsonProperty("company_id") String companyID
    ) {
    }

    public record RoleResponse(
            @JsonProperty("code") Integer code,
            @JsonProperty("description") String description
    ) {
    }

}
