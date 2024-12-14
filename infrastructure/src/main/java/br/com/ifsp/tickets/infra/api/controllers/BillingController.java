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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class BillingController implements BillingAPI {

    private final PaymentService paymentService;
    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Override
    public ResponseEntity<Void> listener(PaymentListenerRequest request, HttpHeaders headers) {
        try {
            if (!this.verifyWebhook(headers, request.data().id().toString(), mercadoPagoAccessToken))
                return ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }

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

    private boolean verifyWebhook(HttpHeaders headers, String id, String secret) throws Exception {
        final String xSignature = headers.getFirst("x-signature");
        final String xRequestId = headers.getFirst("x-request-id");

        assert xSignature != null;
        final String[] parts = xSignature.split(",");
        String ts = null;
        String hash = null;
        for (String part : parts) {
            final String[] keyValue = part.split("=");
            if ("ts".equals(keyValue[0])) {
                ts = keyValue[1];
            } else if ("v1".equals(keyValue[0])) {
                hash = keyValue[1];
            }
        }

        final String manifest = String.format("id:%s;request-id:%s;ts:%s;", id, xRequestId, ts);

        final Mac hmac = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmac.init(secretKeySpec);
        byte[] signature = hmac.doFinal(manifest.getBytes());

        final String computedHash = Base64.getEncoder().encodeToString(signature);
        return computedHash.equals(hash);
    }
}
