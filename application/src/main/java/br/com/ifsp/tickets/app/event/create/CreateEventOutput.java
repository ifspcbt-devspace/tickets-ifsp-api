package br.com.ifsp.tickets.app.event.create;

import br.com.ifsp.tickets.domain.event.Event;

public record CreateEventOutput(
        String id
) {

    public static CreateEventOutput from(Event event) {
        return new CreateEventOutput(event.getId().getValue().toString());
    }

}
