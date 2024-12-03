package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.administrative.event.EventID;

public class AlreadyFinishedEventException extends DomainException {

    public AlreadyFinishedEventException(EventID eventID) {
        super("Event " + eventID.getValue() + " is already finished");
    }
}
