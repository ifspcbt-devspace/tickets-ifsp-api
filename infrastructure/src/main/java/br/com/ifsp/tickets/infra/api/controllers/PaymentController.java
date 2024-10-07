package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.payment.PaymentService;
import br.com.ifsp.tickets.app.payment.create.CreatePaymentInput;
import br.com.ifsp.tickets.app.payment.create.CreatePaymentOutput;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceInput;
import br.com.ifsp.tickets.app.payment.retrieve.PaymentOutput;
import br.com.ifsp.tickets.app.ticket.TicketService;
import br.com.ifsp.tickets.app.ticket.payment.CheckPaymentInput;
import br.com.ifsp.tickets.infra.api.PaymentAPI;
import br.com.ifsp.tickets.infra.contexts.event.sale.payment.models.CreatePaymentRequest;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentController implements PaymentAPI {

    private final PaymentService paymentService;
    private final TicketService ticketService;

    @Override
    public ResponseEntity<String> createPreference(String id) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreatePreferenceInput input = CreatePreferenceInput.of(user.toAggregate(), id);
        String preferenceURL =  paymentService.CreatePreference(input);

        return ResponseEntity.ok(preferenceURL);
    }

    @Override
    public ResponseEntity<Void> paymentWebhook(CreatePaymentRequest request) {
        PaymentOutput p = paymentService.getPayment(request.id());
        CheckPaymentInput checkPaymentInput = new CheckPaymentInput(p.externalReference(), p.status());

        ticketService.changePaymentStatus(checkPaymentInput);

        final CreatePaymentInput input = CreatePaymentInput.of(request.id(), request.dateCreated(), request.action(), p.externalReference());
        CreatePaymentOutput output = paymentService.CreatePayment(input);

        return ResponseEntity.created(URI.create("/v1/payment/" + output.id())).build();
    }
}
