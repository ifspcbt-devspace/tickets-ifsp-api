package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.EnrollmentAPI;
import br.com.ifsp.tickets.infra.contexts.enrollment.models.CreateEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.enrollment.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.contexts.enrollment.presenters.EnrollmentApiPresenter;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
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
    public ResponseEntity<Void> create(CreateEnrollmentRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateEnrollmentInput input = CreateEnrollmentInput.of(
                authenticatedUser.toAggregate(),
                request.name(),
                request.email(),
                request.document(),
                request.birthDate(),
                request.eventId()
        );
        final CreateEnrollmentOutput out = this.enrollmentService.create(input);

        return ResponseEntity.created(URI.create("/v1/enrollment/" + out.enrollmentId())).build();
    }

    @Override
    public ResponseEntity<Pagination<EnrollmentResponse>> findByUser() {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Pagination<EnrollmentResponse> enrollments = this.enrollmentService.listEnrollmentsByUser(authenticatedUser.toAggregate()).map(EnrollmentApiPresenter::present);
        return ResponseEntity.ok(enrollments);
    }
}
