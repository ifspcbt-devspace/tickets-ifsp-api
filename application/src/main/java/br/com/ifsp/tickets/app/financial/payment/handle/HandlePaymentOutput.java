package br.com.ifsp.tickets.app.financial.payment.handle;

import br.com.ifsp.tickets.domain.financial.payment.Payment;

import java.time.LocalDateTime;

public record HandlePaymentOutput(
        String id,
        LocalDateTime paymentDate,
        String externalId
) {

    public static HandlePaymentOutput from(Payment payment) {
        return new HandlePaymentOutput(payment.getId().toString(), payment.getApprovalDate(), payment.getExternalId());
    }
}
