package br.com.ifsp.tickets.infra.contexts.event.sale.ticket;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.contexts.event.sale.ticket.persistence.TicketSaleJpaEntity;
import br.com.ifsp.tickets.infra.contexts.event.sale.ticket.persistence.TicketSaleRepository;
import br.com.ifsp.tickets.infra.contexts.event.sale.ticket.persistence.spec.TicketSaleSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class TicketSaleGateway implements ITicketSaleGateway {
    private final TicketSaleRepository repository;
    private final TicketSaleRepository ticketSaleRepository;

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

        final Page<TicketSale> page = this.repository.findAllByEventID(id.getValue(), request).map(ticketSaleJpaEntity -> ticketSaleJpaEntity.toAggregate());

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Pagination<TicketSale> findAll(AdvancedSearchQuery sq) {
        final TicketSaleSpecificationBuilder specificationBuilder = new TicketSaleSpecificationBuilder();
        sq.filters().forEach(specificationBuilder::with);
        final Specification<TicketSaleJpaEntity> specification = specificationBuilder.build();
        final Sort orders = sq.sorts().stream().map(sort -> Sort.by(Sort.Direction.fromString(sort.direction()), sort.sort())).reduce(Sort::and).orElse(Sort.by(Sort.Order.asc("id")));
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                orders
        );

        final Page<TicketSale> page = this.repository.findAll(specification, request).map(ticketSaleJpaEntity -> ticketSaleJpaEntity.toAggregate());

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
