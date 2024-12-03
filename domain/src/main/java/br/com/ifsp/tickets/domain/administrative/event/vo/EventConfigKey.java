package br.com.ifsp.tickets.domain.administrative.event.vo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public enum EventConfigKey {

    START_SELLING_DATE("Data de início de vendas", LocalDateTime.class),
    END_SELLING_DATE("Data de término de vendas", LocalDateTime.class),
    HAS_DEFAULT_TICKET("Possui ingresso padrão", Boolean.class),
    DEFAULT_TICKET_ID("ID do ingresso padrão", String.class),
    ;

    private final String description;
    private final Class<?> type;

    EventConfigKey(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }
}
