package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.ticket.models.TicketResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/ticket")
@Tag(name = "Ticket", description = "Ticket API - manage tickets for events")
public interface TicketAPI {

    @PatchMapping(value = "/{id}/check", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket checked successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
    })
    ResponseEntity<Void> check(@PathVariable String id);

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
    })
    ResponseEntity<TicketResponse> get(@PathVariable String id);
}
