package br.com.ifsp.tickets.domain.financial.order.item;

import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;

public class OrderItemID extends Identifier<Long> {

    private final Long value;

    public OrderItemID(Long value) {
        this.value = value;
    }

    public static OrderItemID with(Long value) {
        return new OrderItemID(value);
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemID orderID = (OrderItemID) o;
        return Objects.equals(value, orderID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
