package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.infra.api.EnrollmentAPI;
import br.com.ifsp.tickets.infra.contexts.enrollment.models.CreateEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentController implements EnrollmentAPI {
    private final EnrollmentService enrollmentService;

    @Override
    public ResponseEntity<Void> create(@NotNull CreateEnrollmentRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateEnrollmentInput input = CreateEnrollmentInput.of(
                authenticatedUser.toAggregate(),
                request.eventId()
        );
        final CreateEnrollmentOutput out = this.enrollmentService.create(input);

        return ResponseEntity.created(URI.create("/v1/enrollment/" + out.enrollmentId())).build();
    }
}
