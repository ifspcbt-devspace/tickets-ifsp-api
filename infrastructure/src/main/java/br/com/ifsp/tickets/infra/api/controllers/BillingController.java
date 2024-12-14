package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.financial.payment.PaymentService;
import br.com.ifsp.tickets.app.financial.payment.handle.HandlePaymentInput;
import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;
import br.com.ifsp.tickets.infra.api.BillingAPI;
import br.com.ifsp.tickets.infra.contexts.financial.payment.models.PaymentListenerRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class BillingController implements BillingAPI {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<Void> listener(PaymentListenerRequest request) {
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
