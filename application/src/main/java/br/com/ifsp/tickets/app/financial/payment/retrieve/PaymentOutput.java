package br.com.ifsp.tickets.app.financial.payment.retrieve;

import com.mercadopago.resources.payment.Payment;

import java.time.LocalDateTime;

public record PaymentOutput(
        LocalDateTime paymentDate,
        String status,
        String externalReference
) {

    public static PaymentOutput from(LocalDateTime paymentDate, String status, String externalReference) {
        return new PaymentOutput(paymentDate, status, externalReference);
    }

    public static PaymentOutput from(Payment payment) {
        return new PaymentOutput(payment.getDateCreated().toLocalDateTime(), payment.getStatus(), payment.getExternalReference());
    }

}
