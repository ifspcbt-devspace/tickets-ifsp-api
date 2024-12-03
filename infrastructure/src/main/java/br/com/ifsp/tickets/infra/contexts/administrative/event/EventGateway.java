package br.com.ifsp.tickets.infra.contexts.administrative.event;

import br.com.ifsp.tickets.domain.administrative.company.CompanyID;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.contexts.administrative.event.persistence.EventJpaEntity;
import br.com.ifsp.tickets.infra.contexts.administrative.event.persistence.EventRepository;
import br.com.ifsp.tickets.infra.contexts.administrative.event.persistence.spec.EventSpecificationBuilder;
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
public class EventGateway implements IEventGateway {

    private final EventRepository repository;
    private final IFileStorage fileStorage;

    @Override
    public Event create(Event event) {
        return this.repository.save(EventJpaEntity.from(event)).toAggregate(fileStorage);
    }

    @Override
    public Optional<Event> findById(EventID id) {
        return this.repository.findById(id.getValue()).map(eventJpaEntity -> eventJpaEntity.toAggregate(fileStorage));
    }

    @Override
    public Pagination<Event> findAllByCompanyID(CompanyID id, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<Event> page = this.repository.findAllByCompanyId(id.getValue(), request).map(eventJpaEntity -> eventJpaEntity.toAggregate(fileStorage));

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Pagination<Event> findAll(AdvancedSearchQuery sq) {
        final EventSpecificationBuilder specificationBuilder = new EventSpecificationBuilder();
        sq.filters().forEach(specificationBuilder::with);
        final Specification<EventJpaEntity> specification = specificationBuilder.build();
        final Sort orders = sq.sorts().stream().map(sort -> Sort.by(Sort.Direction.fromString(sort.direction()), sort.sort())).reduce(Sort::and).orElse(Sort.by(Sort.Order.asc("id")));
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                orders
        );

        final Page<Event> page = this.repository.findAll(specification, request).map(eventJpaEntity -> eventJpaEntity.toAggregate(fileStorage));

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Event update(Event event) {
        return this.repository.save(EventJpaEntity.from(event)).toAggregate(fileStorage);
    }

    @Override
    public void delete(Event id) {
        this.repository.deleteById(id.getId().getValue());
    }

    @Override
    public boolean exists(EventID id) {
        return this.repository.existsById(id.getValue());
    }
}
