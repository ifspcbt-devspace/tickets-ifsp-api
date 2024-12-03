package br.com.ifsp.tickets.domain.administrative.event.vo;

import lombok.Getter;

@Getter
public enum EventConfigKey {

    UNLIMITED_ENTRIES("Entradas ilimitados", Boolean.class),
    MAX_AVAILABLE_ENTRIES("Número máximo de entradas disponíveis", Integer.class),
    HAS_DEFAULT_TICKET("Possui ingresso padrão", Boolean.class),
    DEFAULT_TICKET_ID("ID do ingresso padrão", String.class),
    SINGLE_TICKET("Ingresso único", Boolean.class),
    ;

    private final String description;
    private final Class<?> type;

    EventConfigKey(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }
}
