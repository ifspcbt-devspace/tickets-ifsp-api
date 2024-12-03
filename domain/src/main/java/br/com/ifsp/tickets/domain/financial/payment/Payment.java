package br.com.ifsp.tickets.domain.financial.payment;

import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Payment extends AggregateRoot<PaymentID> {

    private final String externalId;
    private PaymentStatus status;
    private final OrderID orderId;
    private final String currency;
    private final BigDecimal amount;
    private final String paymentType;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvalDate;

    public Payment(PaymentID id, String externalId, PaymentStatus status, OrderID orderId, String currency, BigDecimal amount, String paymentType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime approvalDate) {
        super(id);
        this.externalId = externalId;
        this.status = status;
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
        this.paymentType = paymentType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.approvalDate = approvalDate;
    }

    public static Payment with(PaymentID id, String externalId, PaymentStatus status, OrderID orderId, String currency, BigDecimal amount, String paymentType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime approvalDate) {
        return new Payment(id, externalId, status, orderId, currency, amount, paymentType, createdAt, updatedAt, approvalDate);
    }

    public static Payment newPayment(String externalId, PaymentStatus status, OrderID orderId, String currency, BigDecimal amount, String paymentType, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime approvalDate) {
        return new Payment(new PaymentID(null), externalId, status, orderId, currency, amount, paymentType, createdAt, updatedAt, approvalDate);
    }

    public void changeStatus(PaymentStatus status) {
        if (this.status == status) {
            return;
        }
        this.status = status;
        this.updatedAt = LocalDateTime.now();

        if (status == PaymentStatus.APPROVED) {
            this.approvalDate = LocalDateTime.now();
        }
    }

    @Override
    public void validate(IValidationHandler handler) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
