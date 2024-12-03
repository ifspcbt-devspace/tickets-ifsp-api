package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.CreateEventRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.event.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.CreateTicketSaleRequest;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/event")
@Tag(name = "Event", description = "Event API - manage events from a company")
public interface EventAPI {

    @PostMapping(value = "/", consumes = "application/json")
    @Operation(
            summary = "Create event",
            description = "Create a new event",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Event created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> create(@RequestBody CreateEventRequest request);

    @PostMapping(
            value = "/search",
            produces = "application/json")
    @Operation(
            summary = "Search events",
            description = "Search events by name, date, location, or category",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Pagination<SearchEventResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                           @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            summary = "Get event by id",
            description = "Get event information by id",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<EventResponse> get(@PathVariable String id);

    @GetMapping(
            value = "/{id}/thumbnail"
    )
    @Operation(
            summary = "Obter thumbnail",
            description = "Obter thumbnail do evento.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Thumbnail found", content = @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Thumbnail not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            },
            method = "GET"
    )
    ResponseEntity<byte[]> getThumbnail(@PathVariable String id);

    @DeleteMapping(
            value = "/{id}/thumbnail"
    )
    @Operation(
            summary = "Deletar thumbnail",
            description = "Deletar thumbnail do evento.",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Thumbnail deleted"),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Thumbnail not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<?> deleteThumbnail(@PathVariable String id);

    @PostMapping(
            value = "/{id}/thumbnail",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Upload thumbnail",
            description = "Upload thumbnail do evento.",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "202", description = "Thumbnail uploaded"),
                    @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Event not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<?> uploadThumbnail(@PathVariable String id, @RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName);

    @PostMapping(
            value = "/{id}/ticketSale", consumes = "application/json")
    @Operation(
            summary = "Create ticket sale",
            description = "Create a new ticket sale",
            security = {
                    @SecurityRequirement(name = "bearer")
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ticket Sale created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> createTicketSale(@PathVariable String id, @RequestBody CreateTicketSaleRequest request);

    @GetMapping(
            value = "/{id}/ticketSale", consumes = "application/json")
    @Operation(
            summary = "Get ticket sale by event id",
            description = "Get ticket sale by event id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket Sale list"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Pagination<TicketSaleResponse>> getTicketSaleByEventId(@PathVariable String id);
}
