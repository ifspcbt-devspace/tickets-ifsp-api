package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.event.EventID;

public class EventThumbnailException extends DomainException {

    public EventThumbnailException(EventID eventID, String filename) {
        super("Event " + eventID.getValue() + " thumbnail(%s) not found, please reset it.".formatted(filename));
    }
}
