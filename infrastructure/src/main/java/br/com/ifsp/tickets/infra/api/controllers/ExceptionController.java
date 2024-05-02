package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.domain.shared.exceptions.DomainException;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalCommandField;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.exceptions.ValidationException;
import br.com.ifsp.tickets.domain.shared.validation.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CustomErrorResponse> handleNotificationException(ValidationException ex, HttpServletRequest request) {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors().stream().map(ErrorResponse::from).toList()
        );
        log.warn(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalCommandField.class)
    public ResponseEntity<CustomErrorResponse> handleIllegalCommandField(IllegalCommandField ex, HttpServletRequest request) {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors().stream().map(ErrorResponse::from).toList()
        );
        log.warn(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                null
        );
        log.warn(ex.getMessage(), ex.getCause());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                null
        );
        log.warn(ex.getMessage(), ex.getCause());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<CustomErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
        log.warn(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CustomErrorResponse(
            @JsonProperty("message")
            String message,
            @JsonProperty("status")
            int status,
            @JsonProperty("errors")
            List<ErrorResponse> errors
    ) {
    }

    public record ErrorResponse(
            @JsonProperty("message")
            String message
    ) {
        public static ErrorResponse from(Error error) {
            return new ErrorResponse(error.message());
        }
    }

}
