package br.com.ifsp.tickets.app.event;

import br.com.ifsp.tickets.app.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.app.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.SearchEventOutput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class EventService {

    private final ICreateEventUseCase createEventUseCase;
    private final IGetEventUseCase getEventUseCase;
    private final ISearchEventUseCase searchEventUseCase;

    public EventService(ICreateEventUseCase createEventUseCase, IGetEventUseCase getEventUseCase, ISearchEventUseCase searchEventUseCase) {
        this.createEventUseCase = createEventUseCase;
        this.getEventUseCase = getEventUseCase;
        this.searchEventUseCase = searchEventUseCase;
    }

    public void create(CreateEventInput input) {
        this.createEventUseCase.execute(input);
    }

    public EventOutput get(String anIn) {
        return this.getEventUseCase.execute(anIn);
    }

    public Pagination<SearchEventOutput> search(AdvancedSearchQuery anIn) {
        return this.searchEventUseCase.execute(anIn);
    }
}
