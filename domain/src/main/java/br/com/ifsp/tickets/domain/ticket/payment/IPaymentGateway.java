package br.com.ifsp.tickets.domain.ticket.payment;


public interface IPaymentGateway {
    Payment create(Payment payment);
}
