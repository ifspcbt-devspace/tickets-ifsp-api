package br.com.ifsp.tickets.infra.contexts.financial.product;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.contexts.financial.product.persistence.TicketSaleJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.product.persistence.TicketSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class TicketSaleGateway implements ITicketSaleGateway {
    private final TicketSaleRepository repository;

    @Override
    public TicketSale create(TicketSale ticketSale) {
        return this.repository.save(TicketSaleJpaEntity.from((ticketSale))).toAggregate();
    }

    @Override
    public Optional<TicketSale> findById(TicketSaleID id) {
        return this.repository.findById(id.getValue()).map(TicketSaleJpaEntity::toAggregate);
    }

    @Override
    public Pagination<TicketSale> findAllByEventID(EventID id, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<TicketSale> page = this.repository.findAllByEventIdAndActiveIsTrue(id.getValue(), request).map(TicketSaleJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public TicketSale update(TicketSale ticketSale) {
        return this.repository.save(TicketSaleJpaEntity.from(ticketSale)).toAggregate();
    }

    @Override
    public void delete(TicketSale id) {
        this.repository.deleteById(id.getId().getValue());
    }

    @Override
    public boolean exists(TicketSaleID id) {
        return this.repository.existsById(id.getValue());
    }
}
