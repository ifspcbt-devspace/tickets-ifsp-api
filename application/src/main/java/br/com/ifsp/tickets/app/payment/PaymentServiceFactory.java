package br.com.ifsp.tickets.app.payment;

import br.com.ifsp.tickets.app.payment.create.CreatePaymentUseCase;
import br.com.ifsp.tickets.app.payment.preference.create.CreatePreferenceUseCase;
import br.com.ifsp.tickets.app.payment.retrieve.GetPaymentUseCase;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.payment.IPaymentGateway;

public class PaymentServiceFactory {
    private static PaymentService paymentService;

    public static PaymentService create(
            ITicketGateway ticketGateway,
            ITicketSaleGateway ticketSaleGateway,
            IPaymentGateway paymentGateway) {
        if (paymentService == null) {
            final CreatePreferenceUseCase createPreferenceUseCase = new CreatePreferenceUseCase(ticketGateway, ticketSaleGateway);
            final CreatePaymentUseCase paymentUseCase = new CreatePaymentUseCase(paymentGateway);
            final GetPaymentUseCase getPaymentUseCase = new GetPaymentUseCase();
            paymentService = new PaymentService(createPreferenceUseCase, getPaymentUseCase, paymentUseCase);
        }
        return paymentService;
    }
}
