package br.com.ifsp.tickets.infra.contexts.financial.order;

import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.contexts.financial.order.persistence.OrderJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.order.persistence.OrderRepository;
import br.com.ifsp.tickets.infra.contexts.financial.order.persistence.spec.OrderSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class OrderGateway implements IOrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        return this.orderRepository.save(OrderJpaEntity.from(order)).toAggregate();
    }

    @Override
    public Optional<Order> findById(OrderID id) {
        return this.orderRepository.findById(id.getValue()).map(OrderJpaEntity::toAggregate);
    }

    @Override
    public Optional<Order> findByIdAndDocument(OrderID id, Document document) {
        return this.orderRepository.findByIdAndDocument(id.getValue(), document.toString()).map(OrderJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Order> findAll(AdvancedSearchQuery advancedSearchQuery) {
        final OrderSpecificationBuilder specificationBuilder = new OrderSpecificationBuilder();
        advancedSearchQuery.filters().forEach(specificationBuilder::with);
        final var specification = specificationBuilder.build();
        final var orders = advancedSearchQuery.sorts().stream()
                .map(sort -> Sort.by(Sort.Direction.fromString(sort.direction()), sort.sort()))
                .reduce(Sort::and).orElse(Sort.by(Sort.Order.asc("id")));
        final var request = PageRequest.of(
                advancedSearchQuery.page(),
                advancedSearchQuery.perPage(),
                orders
        );

        final var page = this.orderRepository.findAll(specification, request).map(OrderJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Order update(Order order) {
        return this.orderRepository.save(OrderJpaEntity.from(order)).toAggregate();
    }

    @Override
    public void delete(Order order) {
        this.orderRepository.delete(OrderJpaEntity.from(order));
    }

    @Override
    public boolean exists(OrderID id) {
        return this.orderRepository.existsById(id.getValue());
    }
}
