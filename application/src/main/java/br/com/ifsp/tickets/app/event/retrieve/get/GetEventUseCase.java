package br.com.ifsp.tickets.app.event.retrieve.get;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

public class GetEventUseCase implements IGetEventUseCase {

    private final IEventGateway eventGateway;

    public GetEventUseCase(IEventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public EventOutput execute(String anIn) {
        final EventID eventID = EventID.with(anIn);
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        return EventOutput.from(event);
    }
}
