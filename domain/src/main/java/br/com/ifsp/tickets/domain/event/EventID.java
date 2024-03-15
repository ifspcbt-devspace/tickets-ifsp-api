package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.UUID;

public class EventID extends Identifier<UUID> {

    private final UUID value;

    public EventID(UUID value) {
        this.value = value;
    }

    public static EventID with(String value) {
        return new EventID(UUIDUtils.getFromString(value));
    }

    public static EventID with(UUID value) {
        return new EventID(value);
    }

    public static EventID unique() {
        return new EventID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

}
