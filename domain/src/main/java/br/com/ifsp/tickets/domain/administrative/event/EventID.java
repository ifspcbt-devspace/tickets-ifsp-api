package br.com.ifsp.tickets.domain.administrative.event;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventID eventID = (EventID) o;
        return Objects.equals(value, eventID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
