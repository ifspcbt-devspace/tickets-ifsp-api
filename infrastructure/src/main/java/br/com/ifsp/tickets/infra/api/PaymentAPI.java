package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.event.sale.payment.models.CreatePaymentRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/payment")
@Tag(name = "Payment", description = "Payment API - manage payment gateway (MERCADO PAGO)")
public interface PaymentAPI {

    @PostMapping(consumes = "application/json", value = "/preference/ticket/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Preference created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<String> createPreference(@PathVariable String id);

    @PostMapping(consumes = "application/json", value = "/webhook")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Webhook made successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> paymentWebhook(@RequestBody CreatePaymentRequest request);
}
