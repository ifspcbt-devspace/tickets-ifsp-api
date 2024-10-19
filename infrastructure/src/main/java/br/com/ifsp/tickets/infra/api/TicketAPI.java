package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.ticket.models.TicketResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/ticket")
@Tag(name = "Ticket", description = "Ticket API - manage tickets for events")
public interface TicketAPI {

    @PatchMapping(value = "/{id}/check", produces = "application/json")
    @Operation(
            summary = "Check ticket",
            description = "Check ticket by id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "202", description = "Ticket checked successfully"),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            }
    )
    ResponseEntity<Void> check(@PathVariable String id);

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Get ticket by id",
            description = "Get ticket information by id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<TicketResponse> get(@PathVariable String id);


    @GetMapping(value = "/search/user/{id}", produces = "application/json")
    @Operation(
            summary = "Search tickets by user",
            description = "Search tickets by user id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Tickets not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Pagination<TicketResponse>> simpleSearch(@PathVariable String id, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                            @RequestParam(name = "terms", required = false, defaultValue = "") String terms);
}
