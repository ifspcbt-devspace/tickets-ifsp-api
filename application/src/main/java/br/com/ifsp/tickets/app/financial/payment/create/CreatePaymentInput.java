package br.com.ifsp.tickets.app.financial.payment.create;

import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePaymentInput(
        String externalId,
        Long orderId,
        PaymentStatus status,
        String currency,
        BigDecimal amount,
        String paymentType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime approvalDate
) {

    public static CreatePaymentInput of(
            String externalId,
            Long orderId,
            PaymentStatus status,
            String currency,
            BigDecimal amount,
            String paymentType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime approvalDate
    ) {
        return new CreatePaymentInput(
                externalId,
                orderId,
                status,
                currency,
                amount,
                paymentType,
                createdAt,
                updatedAt,
                approvalDate
        );
    }

}
