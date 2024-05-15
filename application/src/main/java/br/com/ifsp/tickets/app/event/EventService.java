package br.com.ifsp.tickets.app.event;

import br.com.ifsp.tickets.app.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.event.get.EventOutput;
import br.com.ifsp.tickets.app.event.get.IGetEventUseCase;

public class EventService {

    private final ICreateEventUseCase createEventUseCase;
    private final IGetEventUseCase getEventUseCase;

    public EventService(ICreateEventUseCase createEventUseCase, IGetEventUseCase getEventUseCase) {
        this.createEventUseCase = createEventUseCase;
        this.getEventUseCase = getEventUseCase;
    }

    public void create(CreateEventInput input) {
        this.createEventUseCase.execute(input);
    }

    public EventOutput get(String anIn) {
        return this.getEventUseCase.execute(anIn);
    }
}
