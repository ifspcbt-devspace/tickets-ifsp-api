package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.financial.product.ProductService;
import br.com.ifsp.tickets.app.financial.product.ProductServiceFactory;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductConfig {

    private final IEventGateway eventGateway;
    private final ITicketSaleGateway ticketSaleGateway;

    @Bean
    public ProductService productService() {
        return ProductServiceFactory.create(
                eventGateway,
                ticketSaleGateway
        );
    }
}
