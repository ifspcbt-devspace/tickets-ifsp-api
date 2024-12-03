package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.financial.payment.PaymentService;
import br.com.ifsp.tickets.app.financial.payment.PaymentServiceFactory;
import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PaymentConfig {

    private final IPaymentGateway paymentGateway;

    @Bean
    public PaymentService paymentService() {
        return PaymentServiceFactory.create(paymentGateway);
    }
}
