package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.financial.payment.models.PaymentListenerRequest;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/billing")
@Tag(name = "Billing", description = "Billing API - manage user's billing")
public interface BillingAPI {

    @PostMapping(consumes = "application/json", value = "/listener")
    @Operation(
            summary = "Payment",
            description = "Receive payment notification",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Notification received successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class), mediaType = "application/json"))
            }
    )
    ResponseEntity<Void> listener(@RequestBody PaymentListenerRequest request, @RequestHeader HttpHeaders headers);

}
