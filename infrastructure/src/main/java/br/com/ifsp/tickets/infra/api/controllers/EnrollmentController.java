package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.auth.AuthService;
import br.com.ifsp.tickets.app.auth.get.GetUserByIdInput;
import br.com.ifsp.tickets.app.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.enrollment.core.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.core.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.enrollment.upsert.create.CreateUpsertEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.GetUpsertEnrollmentInput;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.GetUpsertEnrollmentOutput;
import br.com.ifsp.tickets.app.payment.PaymentService;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceInput;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceOutput;
import br.com.ifsp.tickets.app.payment.retrieve.PaymentOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.infra.api.EnrollmentAPI;
import br.com.ifsp.tickets.infra.contexts.enrollment.core.models.CreateEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.enrollment.core.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.contexts.enrollment.core.presenters.EnrollmentApiPresenter;
import br.com.ifsp.tickets.infra.contexts.enrollment.upsert.models.CreateUpsertEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.event.sale.payment.models.CreatePaymentRequest;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentController implements EnrollmentAPI {

    private final EnrollmentService enrollmentService;
    private final PaymentService paymentService;
    private final AuthService authService;

    @Override
    public ResponseEntity<String> create(CreateEnrollmentRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateEnrollmentInput input = CreateEnrollmentInput.of(
                authenticatedUser.toAggregate().getId().getValue().toString(),
                request.name(),
                request.email(),
                request.document(),
                request.birthDate(),
                request.eventId(),
                request.ticketSaleId(),
                request.ticketId()
        );
        final CreateEnrollmentOutput out = this.enrollmentService.create(input);

        return ResponseEntity.created(URI.create("/v1/enrollment/" + out.enrollmentId())).body(out.ticketId());
    }

    @Override
    public ResponseEntity<Pagination<EnrollmentResponse>> findByUser() {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Pagination<EnrollmentResponse> enrollments = this.enrollmentService.listEnrollmentsByUser(authenticatedUser.toAggregate()).map(EnrollmentApiPresenter::present);
        return ResponseEntity.ok(enrollments);
    }

    @Override
    public ResponseEntity<String> createUpsertEnrollment(CreateUpsertEnrollmentRequest request) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final CreatePreferenceInput input = CreatePreferenceInput.of(authenticatedUser.toAggregate(), request.ticketSaleId());
        CreatePreferenceOutput preferenceOutput =  paymentService.CreatePreference(input);

        final CreateUpsertEnrollmentInput in = CreateUpsertEnrollmentInput.of(
                authenticatedUser.toAggregate(),
                request.name(),
                request.email(),
                request.email(),
                request.birthDate(),
                request.eventId(),
                request.ticketSaleId(),
                preferenceOutput.preferenceUrl(),
                preferenceOutput.ticketId()
        );

        final String out = this.enrollmentService.createUpsertEnrollment(in);

        return ResponseEntity.created(URI.create("/v1/enrollment/" + out)).body(preferenceOutput.preferenceUrl());
    }

    @Override
    public ResponseEntity<Void> webhook(CreatePaymentRequest request) {
        System.out.println(request.data().id());
        PaymentOutput p = this.paymentService.getPayment(request.data().id());
        System.out.println(p.externalReference());
        GetUpsertEnrollmentInput in = new GetUpsertEnrollmentInput(p.externalReference());

        GetUpsertEnrollmentOutput output = this.enrollmentService.getUpsertEnrollment(in);
        final CreateEnrollmentInput input = CreateEnrollmentInput.of(
                output.userID(),
                output.name(),
                output.email(),
                output.document(),
                output.birthDate(),
                output.eventId(),
                output.ticketSaleId(),
                output.ticketID()
        );
        CreateEnrollmentOutput out = this.enrollmentService.create(input);
        System.out.println(out);
        return ResponseEntity.created(URI.create("/v1/enrollment/" + out.enrollmentId())).build();
    }
}
