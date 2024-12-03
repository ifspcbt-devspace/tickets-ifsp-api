package br.com.ifsp.tickets.domain.financial.order;

import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

import java.util.Optional;

public interface IOrderGateway {

    Order create(Order order);

    Optional<Order> findById(OrderID id);

    Optional<Order> findByIdAndDocument(OrderID id, Document document);

    Pagination<Order> findAll(AdvancedSearchQuery advancedSearchQuery);

    Order update(Order order);

    void delete(Order order);

    boolean exists(OrderID id);
}
