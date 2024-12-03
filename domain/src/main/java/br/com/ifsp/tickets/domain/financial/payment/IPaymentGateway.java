package br.com.ifsp.tickets.domain.financial.payment;


public interface IPaymentGateway {
    Payment create(Payment payment);
}
