package br.com.ifsp.tickets.domain.ticket;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

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

}
