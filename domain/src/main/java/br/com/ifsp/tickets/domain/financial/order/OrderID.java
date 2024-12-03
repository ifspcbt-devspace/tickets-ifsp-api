package br.com.ifsp.tickets.domain.financial.order;

import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;

public class OrderID extends Identifier<Long> {

    private final Long value;

    public OrderID(Long value) {
        this.value = value;
    }

    public static OrderID with(Long value) {
        return new OrderID(value);
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderID orderID = (OrderID) o;
        return Objects.equals(value, orderID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
