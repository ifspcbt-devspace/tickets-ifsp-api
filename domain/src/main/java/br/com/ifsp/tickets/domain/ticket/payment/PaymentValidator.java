package br.com.ifsp.tickets.domain.ticket.payment;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class PaymentValidator extends Validator {
    private Payment payment;

    public PaymentValidator(IValidationHandler anHandler, Payment payment) {
        super(anHandler);
        this.payment = payment;
    }
// TODO VALIDAÇÃO DO PAGAMENTO
    @Override
    public void validate() {

    }
}
