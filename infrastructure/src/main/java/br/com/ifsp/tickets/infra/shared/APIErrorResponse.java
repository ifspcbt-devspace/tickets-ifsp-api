package br.com.ifsp.tickets.infra.shared;

import br.com.ifsp.tickets.infra.api.controllers.ExceptionController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record APIErrorResponse(
        @JsonProperty("message")
        String message,
        @JsonProperty("status")
        int status,
        @JsonProperty("errors")
        List<ExceptionController.APIError> errors
) {
}