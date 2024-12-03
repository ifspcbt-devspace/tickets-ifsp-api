package br.com.ifsp.tickets.app.financial.payment;

import br.com.ifsp.tickets.app.financial.payment.handle.HandlePaymentInput;
import br.com.ifsp.tickets.app.financial.payment.handle.HandlePaymentOutput;
import br.com.ifsp.tickets.app.financial.payment.handle.IHandlePaymentUseCase;

public class PaymentService {

    private final IHandlePaymentUseCase createPaymentUseCase;

    public PaymentService(IHandlePaymentUseCase createPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
    }

    public HandlePaymentOutput createPayment(HandlePaymentInput anIn) {
        return this.createPaymentUseCase.execute(anIn);
    }

}
