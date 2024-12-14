package br.com.ifsp.tickets.infra.contexts.financial.order.presenters;

import br.com.ifsp.tickets.app.financial.order.create.CreateOrderOutput;
import br.com.ifsp.tickets.app.financial.order.retrieve.OrderItemOutput;
import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.infra.contexts.financial.order.models.CreateOrderResponse;
import br.com.ifsp.tickets.infra.contexts.financial.order.models.OrderItemResponse;
import br.com.ifsp.tickets.infra.contexts.financial.order.models.OrderResponse;
import br.com.ifsp.tickets.infra.contexts.financial.product.models.TicketSaleResponse;

public interface OrderApiPresenter {

    static CreateOrderResponse present(CreateOrderOutput output) {
        if (output == null) return null;
        return new CreateOrderResponse(output.id(), output.paymentUrl());
    }

    static OrderResponse present(OrderOutput output) {
        if (output == null) return null;
        return new OrderResponse(
                output.id(),
                output.customerID(),
                output.name(),
                output.document(),
                output.email(),
                output.phone(),
                output.birthDate(),
                output.paymentUrl(),
                output.status(),
                output.createdAt(),
                output.updatedAt(),
                output.items().stream().map(OrderApiPresenter::present).toList()
        );
    }

    static OrderItemResponse present(OrderItemOutput output) {
        if (output == null) return null;
        return new OrderItemResponse(
                output.id(),
                present(output.product()),
                output.quantity(),
                output.total()
        );
    }

    static TicketSaleResponse present(Ticket2SellOutput output) {
        return new TicketSaleResponse(
                output.id(),
                output.eventId(),
                output.name(),
                output.description(),
                output.price(),
                output.entries(),
                output.active()
        );
    }

}
