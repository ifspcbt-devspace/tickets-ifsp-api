package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.financial.product.ProductService;
import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellInput;
import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.ProductAPI;
import br.com.ifsp.tickets.infra.contexts.administrative.event.presenters.EventApiPresenter;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.CreateTicketSaleRequest;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController implements ProductAPI {

    private final ProductService productService;

    @Override
    public ResponseEntity<Void> createTicketSale(String id, CreateTicketSaleRequest request) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final CreateTicket2SellInput ticket2SellInput = CreateTicket2SellInput.of(
                user.toAggregate(),
                id,
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.entries()
        );

        final CreateTicket2SellOutput output = this.productService.createTicketForSale(ticket2SellInput);

        return ResponseEntity.created(URI.create("/v1/event/" + output.id())).build();
    }

    @Override
    public ResponseEntity<Pagination<TicketSaleResponse>> getTicketSaleByEventId(String id) {
        final Pagination<TicketSaleResponse> list = this.productService.getTicketSaleByEvent(id).map(EventApiPresenter::present);
        return ResponseEntity.ok(list);
    }
}
