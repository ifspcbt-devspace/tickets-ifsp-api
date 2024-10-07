package br.com.ifsp.tickets.infra.config.app;
import br.com.ifsp.tickets.app.payment.PaymentService;
import br.com.ifsp.tickets.app.payment.PaymentServiceFactory;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.payment.IPaymentGateway;
import br.com.ifsp.tickets.infra.contexts.event.sale.payment.PaymentGateway;
import com.mercadopago.MercadoPagoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class MercadoPagoConfiguration {
    private final ITicketSaleGateway ticketSaleGateway;
    private final ITicketGateway ticketGateway;
    private final IPaymentGateway paymentGateway;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @PostConstruct
    public void initialize() {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
    }

    @Bean
    public PaymentService preferenceService() {
        return PaymentServiceFactory.create(ticketGateway, ticketSaleGateway, paymentGateway);
    }
}
