package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.event.EventService;
import br.com.ifsp.tickets.app.event.EventServiceFactory;
import br.com.ifsp.tickets.app.payment.PaymentService;
import br.com.ifsp.tickets.app.payment.PaymentServiceFactory;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.payment.IPaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PaymentConfig {
    private final ITicketGateway ticketGateway;
    private final ITicketSaleGateway ticketSaleGateway;
    private final IPaymentGateway paymentGateway;

    @Bean
    public PaymentService paymentService() {
        return PaymentServiceFactory.create(ticketGateway, ticketSaleGateway, paymentGateway);
    }
}
