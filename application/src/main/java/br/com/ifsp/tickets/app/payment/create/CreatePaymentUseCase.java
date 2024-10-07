package br.com.ifsp.tickets.app.payment.create;

import br.com.ifsp.tickets.domain.ticket.payment.IPaymentGateway;
import br.com.ifsp.tickets.domain.ticket.payment.Payment;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentID;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;

public class CreatePaymentUseCase implements ICreatePaymentUseCase {
    private final IPaymentGateway paymentGateway;

    public CreatePaymentUseCase(IPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public CreatePaymentOutput execute(CreatePaymentInput anIn) {
        Payment payment = new Payment(PaymentID.with(anIn.id()), PaymentStatus.IN_PROCESS, anIn.paymentDate(), anIn.action(), anIn.externalReference());

        return CreatePaymentOutput.from(paymentGateway.create(payment));
    }
}
