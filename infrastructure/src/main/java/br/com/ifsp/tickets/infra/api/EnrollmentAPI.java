package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.CreateEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.contexts.financial.payment.models.CreatePaymentRequest;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/enrollment")
@Tag(name = "Enrollment", description = "Enrollment API - manage enrollments for events")
public interface EnrollmentAPI {

    @PostMapping(consumes = "application/json")
    @Operation(
            summary = "Create enrollment",
            description = "Create a new enrollment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Enrollment created successfully", content = @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.TEXT_PLAIN_VALUE, examples = @ExampleObject(value = "3266a228-d496-4841-b31c-195d1a3e9ee5"))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class), mediaType = "application/json"))
            }
    )
    ResponseEntity<String> create(@RequestBody CreateEnrollmentRequest request);

    @GetMapping(value = "/list", produces = "application/json")
    @Operation(
            summary = "List enrollments",
            description = "List enrollments by user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Enrollments retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class), mediaType = "application/json"))
            }
    )
    ResponseEntity<Pagination<EnrollmentResponse>> findByUser();


    @PostMapping(consumes = "application/json", value = "/webhook")
    @Operation(
            summary = "Payment",
            description = "Receive payment webhook",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Webhook received successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class), mediaType = "application/json"))
            }
    )
    ResponseEntity<Void> webhook(@RequestBody CreatePaymentRequest request);
}
