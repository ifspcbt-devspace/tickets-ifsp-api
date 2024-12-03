package br.com.ifsp.tickets.app.financial.payment.handle;

import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HandlePaymentInput(
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

    public static HandlePaymentInput of(
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
        return new HandlePaymentInput(
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
