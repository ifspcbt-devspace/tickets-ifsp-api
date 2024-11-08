package br.com.ifsp.tickets.app.payment;

import br.com.ifsp.tickets.app.payment.create.CreatePaymentInput;
import br.com.ifsp.tickets.app.payment.create.CreatePaymentOutput;
import br.com.ifsp.tickets.app.payment.create.ICreatePaymentUseCase;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceInput;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceOutput;
import br.com.ifsp.tickets.app.payment.preference.create.ICreatePreferenceUseCase;
import br.com.ifsp.tickets.app.payment.retrieve.IGetPaymentUseCase;
import br.com.ifsp.tickets.app.payment.retrieve.PaymentOutput;

public class PaymentService {

    private final ICreatePreferenceUseCase createPreferenceUseCase;
    private final IGetPaymentUseCase getPaymentUseCase;
    private final ICreatePaymentUseCase createPaymentUseCase;

    public PaymentService(ICreatePreferenceUseCase createPreferenceUseCase, IGetPaymentUseCase getPaymentUseCase, ICreatePaymentUseCase createPaymentUseCase) {
        this.createPreferenceUseCase = createPreferenceUseCase;
        this.getPaymentUseCase = getPaymentUseCase;
        this.createPaymentUseCase = createPaymentUseCase;
    }

    public CreatePreferenceOutput createPreference(CreatePreferenceInput anIn) {
        return this.createPreferenceUseCase.execute(anIn);
    }

    public CreatePaymentOutput createPayment(CreatePaymentInput anIn) {
        return this.createPaymentUseCase.execute(anIn);
    }

    public PaymentOutput getPayment(String id){
        return this.getPaymentUseCase.execute(id);
    }
}
