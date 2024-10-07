package br.com.ifsp.tickets.infra.contexts.event.sale.payment.persistence;

import br.com.ifsp.tickets.domain.ticket.payment.Payment;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentID;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter
public class PaymentJpaEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "payment_date", nullable = false, updatable = false)
    private LocalDateTime paymentDate;
    @Column(name = "status", nullable = false, updatable = false)
    private String status;
    @Column(name = "external_reference", nullable = false)
    private String externalReference;

    public PaymentJpaEntity(Long id, LocalDateTime paymentDate, String status, String externalReference) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.status = status;
        this.externalReference = externalReference;
    }

    public static PaymentJpaEntity from(Payment payment) {
        return new PaymentJpaEntity(
                payment.getId().getValue(),
                payment.getPaymentDate(),
                payment.getAction(),
                payment.getExternalReference()
        );
    }

    public Payment toAggregate() {
        return new Payment(
                PaymentID.with(this.getId()),
                PaymentStatus.IN_PROCESS,
                this.getPaymentDate(),
                this.getStatus(),
                this.getExternalReference()
        );
    }
}
