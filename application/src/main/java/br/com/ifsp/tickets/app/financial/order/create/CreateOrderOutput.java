package br.com.ifsp.tickets.app.financial.order.create;

import br.com.ifsp.tickets.domain.financial.order.Order;

public record CreateOrderOutput(
        String id,
        String paymentUrl
) {

    public static CreateOrderOutput from(Order order) {
        return new CreateOrderOutput(order.getId().toString(), order.getPaymentUrl());
    }
}
