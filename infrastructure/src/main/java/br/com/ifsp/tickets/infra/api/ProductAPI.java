package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.CreateTicketSaleRequest;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/product")
@Tag(name = "Product", description = "Product API - manage products from a event")
public interface ProductAPI {

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
