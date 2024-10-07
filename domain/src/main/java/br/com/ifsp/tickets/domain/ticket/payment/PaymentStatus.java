package br.com.ifsp.tickets.domain.ticket.payment;

public enum PaymentStatus {
    APPROVED,
    PENDING,
    IN_PROCESS,
    REJECTED,
    CANCELLED,
    REFUNDED,
    CHARGED_BACK;
}
