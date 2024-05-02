package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.user.contexts.recovery.models.RecoveryRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Authentication and Authorization API")
public interface AuthAPI {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @PostMapping(value = "/activate/{token}")
    @ApiResponse(responseCode = "204", description = "User activated successfully")
    ResponseEntity<Void> activate(@PathVariable String token);

    @PostMapping(value = "/recovery/{login}")
    @ApiResponse(responseCode = "204", description = "Recovery request sent successfully")
    ResponseEntity<Void> forgotPassword(@PathVariable String login, HttpServletRequest request);

    @PostMapping(value = "/recovery/change")
    @ApiResponse(responseCode = "204", description = "Password changed successfully")
    ResponseEntity<Void> accountRecovery(@RequestBody RecoveryRequest request);
}
