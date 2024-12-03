package br.com.ifsp.tickets.app.financial.payment.create;

import br.com.ifsp.tickets.domain.financial.payment.Payment;

import java.time.LocalDateTime;

public record CreatePaymentOutput(
        String id,
        LocalDateTime paymentDate,
        String externalReference
) {

    public static CreatePaymentOutput from(Payment payment) {
        return new CreatePaymentOutput(payment.getId().toString(), payment.getPaymentDate(), payment.getAction(), payment.getExternalReference());
    }
}
