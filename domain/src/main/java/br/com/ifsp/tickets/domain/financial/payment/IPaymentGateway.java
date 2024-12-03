package br.com.ifsp.tickets.domain.financial.payment;


import java.util.Optional;

public interface IPaymentGateway {

    Payment create(Payment payment);

    Optional<Payment> findByExternalId(String externalId);

    Payment update(Payment payment);
}
