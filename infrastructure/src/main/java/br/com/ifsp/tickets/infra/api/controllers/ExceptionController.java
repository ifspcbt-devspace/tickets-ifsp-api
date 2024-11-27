package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.domain.shared.exceptions.*;
import br.com.ifsp.tickets.domain.shared.validation.Error;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.stream.Stream;

@ControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<APIErrorResponse> handleNotificationException(ValidationException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors().stream().map(APIError::from).toList()
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(APIErrorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                "Illegal json attribute value",
                HttpStatus.BAD_REQUEST.value(),
                Stream.of(new Error(ex.getMessage())).map(APIError::from).toList()
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(APIErrorResponse);
    }

    @ExceptionHandler(IllegalCommandField.class)
    public ResponseEntity<APIErrorResponse> handleIllegalCommandField(IllegalCommandField ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrors().stream().map(APIError::from).toList()
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(APIErrorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                null
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIErrorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                null
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIErrorResponse);
    }

    @ExceptionHandler(IllegalResourceAccessException.class)
    public ResponseEntity<APIErrorResponse> handleIllegalResourceAccess(IllegalResourceAccessException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                null
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIErrorResponse);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<APIErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {
        final APIErrorResponse APIErrorResponse = new APIErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
        log.error(ex.getMessage(), ex.getCause());

        return ResponseEntity.badRequest().body(APIErrorResponse);
    }

    public record APIError(
            @JsonProperty("message")
            String message
    ) {
        public static APIError from(Error error) {
            return new APIError(error.message());
        }
    }

}
