package br.com.ifsp.tickets.domain.financial.order;

public interface IPaymentURLGenerator {

    String generateURL(Order order);

}
