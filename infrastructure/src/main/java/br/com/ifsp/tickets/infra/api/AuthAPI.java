package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.administrative.user.contexts.recovery.models.RecoveryRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.user.models.register.RegisterRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.user.models.update.UpdateUserRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.user.models.user.UserResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/auth")
@Tag(name = "Auth", description = "Authentication and Authorization API - manage user authentication and authorization based on JWT tokens")
public interface AuthAPI {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Login",
            description = "Authenticate user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Register user",
            description = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get user by id",
            description = "Get user information by id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<UserResponse> getUserById(@PathVariable String id);

    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Update user",
            description = "Update user information",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request);

    @PostMapping(value = "/activate/{token}")
    @Operation(
            summary = "Activate user",
            description = "Activate user account",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User activated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid token", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> activate(@PathVariable String token);

    @PostMapping(value = "/recovery/{login}")
    @Operation(
            summary = "Forgot password",
            description = "Send recovery request to user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Recovery request sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid login", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> forgotPassword(@PathVariable String login, HttpServletRequest request);

    @PostMapping(value = "/recovery/change")
    @Operation(
            summary = "Change password",
            description = "Change user password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password changed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> accountRecovery(@RequestBody RecoveryRequest request);
}
