package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.ticket.models.TicketResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "/search/user/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Tickets not found"),
    })
    ResponseEntity<Pagination<TicketResponse>> simpleSearch(@PathVariable String id, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                            @RequestParam(name = "terms", required = false, defaultValue = "") String terms);
}
