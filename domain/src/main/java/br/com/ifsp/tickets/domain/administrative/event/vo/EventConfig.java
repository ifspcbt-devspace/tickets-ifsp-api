package br.com.ifsp.tickets.domain.administrative.event.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventConfig extends ValueObject {

    private final EventConfigKey key;
    private String value;

    public EventConfig(EventConfigKey key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EventConfig with(EventConfigKey key, String value) {
        return new EventConfig(key, value);
    }

    public void update(String value) {
        this.value = value;
    }

    public boolean getBoolean() {
        if (key.getType() != Boolean.class) {
            throw new IllegalArgumentException("Invalid type");
        }
        return Boolean.parseBoolean(value);
    }

    public int getInteger() {
        if (key.getType() != Integer.class) {
            throw new IllegalArgumentException("Invalid type");
        }
        return Integer.parseInt(value);
    }

    public LocalDateTime getLocalDateTime() {
        if (key.getType() != LocalDateTime.class) {
            throw new IllegalArgumentException("Invalid type");
        }
        return LocalDateTime.parse(value);
    }

    public double getDouble() {
        if (key.getType() != Double.class) {
            throw new IllegalArgumentException("Invalid type");
        }
        return Double.parseDouble(value);
    }
}
