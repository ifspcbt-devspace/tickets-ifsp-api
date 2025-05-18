package br.com.ifsp.tickets.domain.administrative.event.vo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum EventConfigKey {

    START_SELLING_DATE("Data de início de vendas", LocalDateTime.class, true),
    END_SELLING_DATE("Data de término de vendas", LocalDateTime.class, true),
    HAS_DEFAULT_TICKET("Possui ingresso padrão", Boolean.class, true),
    DEFAULT_TICKET_ID("ID do ingresso padrão", String.class, false),
    ;

    private final String description;
    private final Class<?> type;
    private final boolean required;

    EventConfigKey(String description, Class<?> type , boolean required) {
        this.description = description;
        this.type = type;
        this.required = required;
    }
}
