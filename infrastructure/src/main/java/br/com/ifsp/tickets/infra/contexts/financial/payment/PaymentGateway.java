package br.com.ifsp.tickets.infra.contexts.financial.payment;

import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;
import br.com.ifsp.tickets.domain.financial.payment.Payment;
import br.com.ifsp.tickets.infra.contexts.financial.payment.persistence.PaymentJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.payment.persistence.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PaymentGateway implements IPaymentGateway {
    private final PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(PaymentJpaEntity.from(payment)).toAggregate();
    }
}
