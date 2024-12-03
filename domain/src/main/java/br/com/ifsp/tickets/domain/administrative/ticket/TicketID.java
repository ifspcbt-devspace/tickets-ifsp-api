package br.com.ifsp.tickets.domain.administrative.ticket;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class TicketID extends Identifier<UUID> {

    private final UUID value;

    public TicketID(UUID value) {
        this.value = value;
    }

    public static TicketID with(String value) {
        return new TicketID(UUIDUtils.getFromString(value));
    }

    public static TicketID with(UUID value) {
        return new TicketID(value);
    }

    public static TicketID unique() {
        return new TicketID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketID ticketID = (TicketID) o;
        return Objects.equals(value, ticketID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
