package br.com.ifsp.tickets.app.financial.order.retrieve;

import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;

import java.math.BigDecimal;

public record OrderItemOutput(
        String id,
        Ticket2SellOutput product,
        Integer quantity,
        BigDecimal total
) {

    public static OrderItemOutput from(OrderItem orderItem) {
        return new OrderItemOutput(
                orderItem.getId().getValue().toString(),
                Ticket2SellOutput.from(orderItem.getTicket()),
                orderItem.getQuantity(),
                orderItem.getTotal()
        );
    }
}
