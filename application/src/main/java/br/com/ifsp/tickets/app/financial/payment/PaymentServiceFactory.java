package br.com.ifsp.tickets.app.financial.payment;

import br.com.ifsp.tickets.app.financial.payment.create.CreatePaymentUseCase;
import br.com.ifsp.tickets.app.financial.payment.retrieve.GetPaymentUseCase;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;

public class PaymentServiceFactory {
    private static PaymentService paymentService;

    public static PaymentService create(
            IPaymentGateway paymentGateway,
            IOrderGateway orderGateway
    ) {
        if (paymentService == null) {
            final CreatePaymentUseCase paymentUseCase = new CreatePaymentUseCase(paymentGateway, orderGateway);
            final GetPaymentUseCase getPaymentUseCase = new GetPaymentUseCase();
            paymentService = new PaymentService(getPaymentUseCase, paymentUseCase);
        }
        return paymentService;
    }
}
