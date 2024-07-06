package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.event.core.models.CreateEventRequest;
import br.com.ifsp.tickets.infra.contexts.event.core.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.event.core.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/event")
@Tag(name = "Event", description = "Event API - manage events from a company")
public interface EventAPI {

    @PostMapping(consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> create(@RequestBody CreateEventRequest request);

    @PostMapping(
            value = "/search",
            produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events list"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Pagination<SearchEventResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                           @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    ResponseEntity<EventResponse> get(@PathVariable String id);

}
