package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.financial.order.OrderService;
import br.com.ifsp.tickets.app.financial.order.cancel.CancelOrderInput;
import br.com.ifsp.tickets.app.financial.order.create.CreateOrderInput;
import br.com.ifsp.tickets.app.financial.order.create.CreateOrderOutput;
import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.app.financial.order.retrieve.get.GetOrderByIDInput;
import br.com.ifsp.tickets.app.financial.order.retrieve.list.ListCustomerOrdersInput;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;
import br.com.ifsp.tickets.infra.api.OrderAPI;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.order.models.*;
import br.com.ifsp.tickets.infra.contexts.financial.order.presenters.OrderApiPresenter;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import br.com.ifsp.tickets.infra.shared.search.SearchFilterRequest;
import br.com.ifsp.tickets.infra.shared.search.SortSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class OrderController implements OrderAPI {

    private final OrderService orderService;

    @Override
    public ResponseEntity<CreateOrderResponse> pay(CreateOrderRequest request) {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = principal == null ? null : ((UserJpaEntity) principal).toAggregate();
        final CreateOrderInput input = CreateOrderInput.of(
                user,
                request.name(),
                request.email(),
                request.phone(),
                request.document(),
                request.birthDate(),
                request.items().stream().map(i -> CreateOrderInput.OrderItemInput.of(UUIDUtils.getFromString(i.ticketSaleId()))).toList()
        );

        final CreateOrderOutput order = this.orderService.createOrder(input);
        return ResponseEntity.ok(OrderApiPresenter.present(order));
    }

    @Override
    public ResponseEntity<Pagination<OrderResponse>> search(Integer page, Integer perPage, AdvancedSearchRequest request) {
        final User user = ((UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).toAggregate();
        final AdvancedSearchQuery advancedSearchQuery = AdvancedSearchQuery.of(
                page,
                perPage,
                request.sorts().stream().map(SortSearchRequest::toSortSearch).toList(),
                request.filters().stream().map(SearchFilterRequest::toSearchFilter).toList()
        );

        final ListCustomerOrdersInput input = ListCustomerOrdersInput.of(user, advancedSearchQuery);
        final Pagination<OrderResponse> pagination = this.orderService.listCustomerOrders(input).map(OrderApiPresenter::present);

        return ResponseEntity.ok(pagination);
    }

    @Override
    public ResponseEntity<OrderResponse> get(Long id, GetOrderRequest request) {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = principal == null ? null : ((UserJpaEntity) principal).toAggregate();
        final String document = request == null ? null : request.document();

        final GetOrderByIDInput input = GetOrderByIDInput.of(
                user,
                document,
                id
        );

        final OrderOutput output = this.orderService.getOrderByID(input);
        return ResponseEntity.ok(OrderApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Void> cancel(Long id, CancelOrderRequest request) {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = principal == null ? null : ((UserJpaEntity) principal).toAggregate();
        final String document = request == null ? null : request.document();
        final CancelOrderInput input = CancelOrderInput.of(
                user,
                document,
                id
        );

        this.orderService.cancelOrder(input);

        return ResponseEntity.noContent().build();
    }
}
