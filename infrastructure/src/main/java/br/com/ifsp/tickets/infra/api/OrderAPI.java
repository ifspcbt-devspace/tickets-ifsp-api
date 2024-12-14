package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.financial.order.models.*;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/order")
@Tag(name = "Order", description = "Order API - manage user's orders")
public interface OrderAPI {

    @PostMapping(consumes = "application/json", produces = "application/json", value = "/pay")
    @Operation(
            summary = "Payment",
            description = "generate payment url",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment url generated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class), mediaType = "application/json"))
            }

    )
    ResponseEntity<CreateOrderResponse> pay(CreateOrderRequest request);

    @PostMapping(
            value = "/search",
            produces = "application/json",
            consumes = "application/json")
    @Operation(
            summary = "Search orders",
            description = "Search orders with advanced filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orders found"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Pagination<OrderResponse>> search(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(name = "perPage", required = false, defaultValue = "10") Integer perPage,
                                                     @RequestBody AdvancedSearchRequest request);

    @GetMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "Get order by id",
            description = "Get order information by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order found"),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<OrderResponse> get(@PathVariable Long id, @RequestBody(required = false) GetOrderRequest request);

    @PostMapping(value = "/{id}/cancel")
    @Operation(
            summary = "Cancel order",
            description = "Cancel order by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order canceled"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<Void> cancel(@PathVariable Long id, @RequestBody(required = false) CancelOrderRequest request);

}
