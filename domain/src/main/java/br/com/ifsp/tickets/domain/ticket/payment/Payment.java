package br.com.ifsp.tickets.domain.ticket.payment;

import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Payment extends AggregateRoot<PaymentID> {
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String action;
    private String externalReference;

    public Payment(PaymentID paymentID, PaymentStatus status, LocalDateTime paymentDate, String action, String externalReference) {
        super(paymentID);
        this.status = status;
        this.paymentDate = paymentDate;
        this.action = action;
        this.externalReference = externalReference;
    }

    @Override
    public void validate(IValidationHandler handler) {
        new PaymentValidator(handler, this).validate();
    }
}
