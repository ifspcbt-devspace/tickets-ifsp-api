package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.shared.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/enrollment")
@Tag(name = "Enrollment", description = "Enrollment API - manage enrollments for events")
public interface EnrollmentAPI {

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

}
