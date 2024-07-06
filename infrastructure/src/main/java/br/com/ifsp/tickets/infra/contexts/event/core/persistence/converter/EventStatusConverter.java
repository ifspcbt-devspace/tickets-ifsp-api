package br.com.ifsp.tickets.infra.contexts.event.core.persistence.converter;

import br.com.ifsp.tickets.domain.event.EventStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EventStatusConverter implements AttributeConverter<EventStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EventStatus eventStatus) {
        return eventStatus.getCode();
    }

    @Override
    public EventStatus convertToEntityAttribute(Integer integer) {
        return EventStatus.fromCode(integer);
    }
}
