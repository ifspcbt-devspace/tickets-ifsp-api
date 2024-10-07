package br.com.ifsp.tickets.app.payment.create;

import br.com.ifsp.tickets.domain.ticket.payment.Payment;

import java.time.LocalDateTime;

public record CreatePaymentOutput(
        String id,
        LocalDateTime paymentDate,
        String action,
        String externalReference
) {

    public static CreatePaymentOutput from(Payment payment) {
        return new CreatePaymentOutput(payment.getId().toString(), payment.getPaymentDate(), payment.getAction(), payment.getExternalReference());
    }
}
