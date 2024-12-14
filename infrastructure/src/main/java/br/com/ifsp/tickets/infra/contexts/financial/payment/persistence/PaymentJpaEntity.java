package br.com.ifsp.tickets.infra.contexts.financial.payment.persistence;

import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.payment.Payment;
import br.com.ifsp.tickets.domain.financial.payment.PaymentID;
import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter
public class PaymentJpaEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "external_id", nullable = false, updatable = false)
    private String externalId;
    @Column(name = "status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;
    @Column(name = "currency", nullable = false, updatable = false)
    private String currency;
    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;
    @Column(name = "payment_type", nullable = false, updatable = false)
    private String paymentType;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;
    @Column(name = "approval_date", nullable = false, updatable = false)
    private LocalDateTime approvalDate;

    public PaymentJpaEntity(
            Long id,
            String externalId,
            PaymentStatus status,
            Long orderId,
            String currency,
            BigDecimal amount,
            String paymentType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime approvalDate
    ) {
        this.id = id;
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

    public static PaymentJpaEntity from(Payment payment) {
        return new PaymentJpaEntity(
                payment.getId().getValue(),
                payment.getExternalId(),
                payment.getStatus(),
                payment.getOrderId().getValue(),
                payment.getCurrency(),
                payment.getAmount(),
                payment.getPaymentType(),
                payment.getCreatedAt(),
                payment.getUpdatedAt(),
                payment.getApprovalDate()
        );
    }

    public Payment toAggregate() {
        return new Payment(
                new PaymentID(this.id),
                this.externalId,
                this.status,
                new OrderID(this.orderId),
                this.currency,
                this.amount,
                this.paymentType,
                this.createdAt,
                this.updatedAt,
                this.approvalDate
        );
    }


}
