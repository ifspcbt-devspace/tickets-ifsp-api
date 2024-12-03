package br.com.ifsp.tickets.app.administrative.event.create;

import br.com.ifsp.tickets.domain.administrative.event.Event;

public record CreateEventOutput(
        String id
) {

    public static CreateEventOutput from(Event event) {
        return new CreateEventOutput(event.getId().getValue().toString());
    }

}
