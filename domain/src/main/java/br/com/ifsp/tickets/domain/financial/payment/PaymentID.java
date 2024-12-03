package br.com.ifsp.tickets.domain.financial.payment;

import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;
import java.util.Random;

public class PaymentID extends Identifier<Long> {

    private final Long value;

    public PaymentID(Long value) {
        this.value = value;
    }

    public static PaymentID with(String value) {
        return new PaymentID(Long.parseLong(value));
    }

    public static PaymentID with(Long value) {
        return new PaymentID(value);
    }

    public static PaymentID unique() {
        return new PaymentID(new Random().nextLong());
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentID that = (PaymentID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
