package br.com.ifsp.tickets.app.financial.payment;

import br.com.ifsp.tickets.app.financial.payment.create.CreatePaymentInput;
import br.com.ifsp.tickets.app.financial.payment.create.CreatePaymentOutput;
import br.com.ifsp.tickets.app.financial.payment.create.ICreatePaymentUseCase;
import br.com.ifsp.tickets.app.financial.payment.retrieve.IGetPaymentUseCase;
import br.com.ifsp.tickets.app.financial.payment.retrieve.PaymentOutput;

public class PaymentService {

    private final IGetPaymentUseCase getPaymentUseCase;
    private final ICreatePaymentUseCase createPaymentUseCase;

    public PaymentService(IGetPaymentUseCase getPaymentUseCase, ICreatePaymentUseCase createPaymentUseCase) {
        this.getPaymentUseCase = getPaymentUseCase;
        this.createPaymentUseCase = createPaymentUseCase;
    }

    public CreatePaymentOutput createPayment(CreatePaymentInput anIn) {
        return this.createPaymentUseCase.execute(anIn);
    }

    public PaymentOutput getPayment(String id) {
        return this.getPaymentUseCase.execute(id);
    }
}
