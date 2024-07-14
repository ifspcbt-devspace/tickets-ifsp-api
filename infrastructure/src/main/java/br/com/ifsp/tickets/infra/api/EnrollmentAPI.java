package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.contexts.enrollment.models.CreateEnrollmentRequest;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/enrollment")
@Tag(name = "Enrollment", description = "Enrollment API - manage enrollments for events")
public interface EnrollmentAPI {

    @PostMapping(consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Enrollment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    ResponseEntity<Void> create(@RequestBody CreateEnrollmentRequest request);
}
