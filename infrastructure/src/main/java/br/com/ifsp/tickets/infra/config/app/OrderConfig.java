package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.financial.order.OrderService;
import br.com.ifsp.tickets.app.financial.order.OrderServiceFactory;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.IPaymentURLGenerator;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderConfig {

    private final IPaymentURLGenerator paymentURLGenerator;
    private final IOrderGateway orderGateway;
    private final IEventGateway eventGateway;
    private final ITicketSaleGateway ticketSaleGateway;

    @Bean
    public OrderService orderService() {
        return OrderServiceFactory.create(
                paymentURLGenerator,
                eventGateway,
                orderGateway,
                ticketSaleGateway
        );
    }
}
