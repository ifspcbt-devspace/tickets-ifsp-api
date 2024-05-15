package br.com.ifsp.tickets.domain.event;

import lombok.Getter;

@Getter
public enum EventConfigKey {

    UNLIMITED_TICKETS("Ingressos ilimitados", Boolean.class),
    MAX_AVAILABLE_TICKETS("Número máximo de ingressos disponíveis", Integer.class),
    ;

    private final String description;
    private final Class<?> type;

    EventConfigKey(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }
}
