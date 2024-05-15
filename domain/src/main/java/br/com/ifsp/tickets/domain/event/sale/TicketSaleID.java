package br.com.ifsp.tickets.domain.event.sale;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class TicketSaleID extends Identifier<UUID> {

    private final UUID value;

    public TicketSaleID(UUID value) {
        this.value = value;
    }

    public static TicketSaleID with(String value) {
        return new TicketSaleID(UUIDUtils.getFromString(value));
    }

    public static TicketSaleID with(UUID value) {
        return new TicketSaleID(value);
    }

    public static TicketSaleID unique() {
        return new TicketSaleID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketSaleID that = (TicketSaleID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
