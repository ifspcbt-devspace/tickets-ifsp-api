package br.com.ifsp.tickets.app.payment.create;

import java.time.LocalDateTime;

public record CreatePaymentInput(
        String id,
        LocalDateTime paymentDate,
        String action,
        String externalReference
) {
    public static CreatePaymentInput of(String id, LocalDateTime paymentDate, String action, String externalReference) {
        return new CreatePaymentInput(id, paymentDate, action, externalReference);
    }
}
