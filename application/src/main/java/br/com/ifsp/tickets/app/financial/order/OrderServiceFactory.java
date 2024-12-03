package br.com.ifsp.tickets.app.financial.order;

import br.com.ifsp.tickets.app.financial.order.cancel.CancelOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.cancel.ICancelOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.create.CreateOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.create.ICreateOrderUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.get.GetOrderByIDUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.get.IGetOrderByIDUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.list.IListCustomerOrdersUseCase;
import br.com.ifsp.tickets.app.financial.order.retrieve.list.ListCustomerOrdersUseCase;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.IPaymentURLGenerator;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;

public class OrderServiceFactory {

    private static OrderService orderService;

    public static OrderService create(
            IPaymentURLGenerator paymentURLGenerator,
            IEventGateway eventGateway,
            IOrderGateway orderGateway,
            ITicketSaleGateway ticketSaleGateway) {
        if (orderService == null) {
            final ICreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(orderGateway, eventGateway, paymentURLGenerator, ticketSaleGateway);
            final IGetOrderByIDUseCase getOrderByIDUseCase = new GetOrderByIDUseCase(orderGateway);
            final IListCustomerOrdersUseCase listCustomerOrdersUseCase = new ListCustomerOrdersUseCase(orderGateway);
            final ICancelOrderUseCase cancelOrderUseCase = new CancelOrderUseCase(orderGateway, ticketSaleGateway);
            orderService = new OrderService(createOrderUseCase, getOrderByIDUseCase, listCustomerOrdersUseCase, cancelOrderUseCase);
        }
        return orderService;
    }

}
