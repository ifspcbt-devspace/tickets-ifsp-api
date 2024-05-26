package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.user.contexts.recovery.models.RecoveryRequest;
import br.com.ifsp.tickets.infra.contexts.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.contexts.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.contexts.user.models.register.RegisterRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Authentication and Authorization API - manage user authentication and authorization based on JWT tokens")
public interface AuthAPI {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @PostMapping(value = "/activate/{token}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User activated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid token")
    })
    ResponseEntity<Void> activate(@PathVariable String token);

    @PostMapping(value = "/recovery/{login}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recovery request sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid login")
    })
    ResponseEntity<Void> forgotPassword(@PathVariable String login, HttpServletRequest request);

    @PostMapping(value = "/recovery/change")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> accountRecovery(@RequestBody RecoveryRequest request);
}
