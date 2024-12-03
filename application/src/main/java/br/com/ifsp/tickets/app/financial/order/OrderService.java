package br.com.ifsp.tickets.app.financial.order;

import br.com.ifsp.tickets.app.financial.order.cancel.CancelOrderInput;
import br.com.ifsp.tickets.app.financial.order.cancel.ICancelOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.create.CreateOrderInput;
import br.com.ifsp.tickets.app.financial.order.create.CreateOrderOutput;
import br.com.ifsp.tickets.app.financial.order.create.ICreateOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.app.financial.order.retrieve.get.GetOrderByIDInput;
import br.com.ifsp.tickets.app.financial.order.retrieve.get.IGetOrderByIDUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.list.IListCustomerOrdersUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.list.ListCustomerOrdersInput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class OrderService {

    private final ICreateOrderUseCase createOrderUseCase;
    private final IGetOrderByIDUseCase getOrderByIDUseCase;
    private final IListCustomerOrdersUseCase listCustomerOrdersUseCase;
    private final ICancelOrderUseCase cancelOrderUseCase;

    public OrderService(ICreateOrderUseCase createOrderUseCase, IGetOrderByIDUseCase getOrderByIDUseCase, IListCustomerOrdersUseCase listCustomerOrdersUseCase, ICancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIDUseCase = getOrderByIDUseCase;
        this.listCustomerOrdersUseCase = listCustomerOrdersUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    public CreateOrderOutput createOrder(CreateOrderInput anIn) {
        return this.createOrderUseCase.execute(anIn);
    }

    public OrderOutput getOrderByID(GetOrderByIDInput input) {
        return this.getOrderByIDUseCase.execute(input);
    }

    public Pagination<OrderOutput> listCustomerOrders(ListCustomerOrdersInput input) {
        return this.listCustomerOrdersUseCase.execute(input);
    }

    public void cancelOrder(CancelOrderInput anIn) {
        this.cancelOrderUseCase.execute(anIn);
    }
}
