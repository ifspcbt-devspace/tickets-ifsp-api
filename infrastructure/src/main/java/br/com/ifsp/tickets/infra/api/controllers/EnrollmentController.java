package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.administrative.enrollment.EnrollmentService;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.EnrollmentAPI;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.presenters.EnrollmentApiPresenter;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentController implements EnrollmentAPI {

    private final EnrollmentService enrollmentService;

    @Override
    public ResponseEntity<Pagination<EnrollmentResponse>> findByUser() {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Pagination<EnrollmentResponse> enrollments = this.enrollmentService.listEnrollmentsByUser(authenticatedUser.toAggregate()).map(EnrollmentApiPresenter::present);
        return ResponseEntity.ok(enrollments);
    }

}
