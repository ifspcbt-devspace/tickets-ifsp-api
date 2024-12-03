package br.com.ifsp.tickets.domain.administrative.event.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import lombok.Getter;

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

    public boolean getValueAsBoolean() {
        return Boolean.parseBoolean(value);
    }

    public int getValueAsInteger() {
        return Integer.parseInt(value);
    }

    public double getValueAsDouble() {
        return Double.parseDouble(value);
    }
}
