package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.administrative.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.administrative.enrollment.core.create.CreateEnrollmentInput;
import br.com.ifsp.tickets.app.administrative.enrollment.core.create.CreateEnrollmentOutput;
import br.com.ifsp.tickets.app.financial.payment.PaymentService;
import br.com.ifsp.tickets.app.financial.payment.handle.HandlePaymentInput;
import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.EnrollmentAPI;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.CreateEnrollmentRequest;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.models.EnrollmentResponse;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.presenters.EnrollmentApiPresenter;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.payment.models.CreatePaymentRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
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
    private final PaymentService paymentService;

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
    public ResponseEntity<Void> webhook(CreatePaymentRequest request) {
        final PaymentClient paymentClient = new PaymentClient();
        final Payment payment;
        try {
            payment = paymentClient.get(request.data().id());
        } catch (MPException | MPApiException e) {
            return ResponseEntity.badRequest().build();
        }

        final HandlePaymentInput input = HandlePaymentInput.of(
                payment.getId().toString(),
                Long.valueOf(payment.getExternalReference()),
                PaymentStatus.valueOf(payment.getStatus().toUpperCase()),
                payment.getCurrencyId(),
                payment.getTransactionAmount(),
                payment.getPaymentTypeId(),
                payment.getDateCreated().toLocalDateTime(),
                payment.getDateLastUpdated().toLocalDateTime(),
                payment.getDateApproved() == null ? null : payment.getDateApproved().toLocalDateTime()
        );

        this.paymentService.handle(input);
        return ResponseEntity.ok().build();
    }
}
