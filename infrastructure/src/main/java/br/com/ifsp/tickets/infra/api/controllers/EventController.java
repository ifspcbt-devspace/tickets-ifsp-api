package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.event.EventService;
import br.com.ifsp.tickets.app.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.event.create.CreateEventOutput;
import br.com.ifsp.tickets.app.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.infra.api.EventAPI;
import br.com.ifsp.tickets.infra.contexts.event.core.models.CreateEventRequest;
import br.com.ifsp.tickets.infra.contexts.event.core.models.EventResponse;
import br.com.ifsp.tickets.infra.contexts.event.core.models.SearchEventResponse;
import br.com.ifsp.tickets.infra.contexts.event.core.presenters.EventApiPresenter;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.shared.search.AdvancedSearchRequest;
import br.com.ifsp.tickets.infra.shared.search.SearchFilterRequest;
import br.com.ifsp.tickets.infra.shared.search.SortSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController implements EventAPI {

    private final EventService eventService;

    @Override
    public ResponseEntity<Void> create(CreateEventRequest request) {
        final UserJpaEntity user = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CreateEventInput input = CreateEventInput.with(
                user.toAggregate(),
                request.companyId(),
                request.name(),
                request.description(),
                request.initDate(),
                request.endDate(),
                request.configuration()
        );
        final CreateEventOutput output = this.eventService.create(input);
        return ResponseEntity.created(URI.create("/v1/event/" + output.id())).build();
    }

    @Override
    public ResponseEntity<Pagination<SearchEventResponse>> search(Integer page, Integer perPage, AdvancedSearchRequest request) {
        final AdvancedSearchQuery advancedSearchQuery = AdvancedSearchQuery.of(
                page,
                perPage,
                request.sorts().stream().map(SortSearchRequest::toSortSearch).toList(),
                request.filters().stream().map(SearchFilterRequest::toSearchFilter).toList()
        );
        final Pagination<SearchEventResponse> pagination = this.eventService.search(advancedSearchQuery).map(EventApiPresenter::present);
        return ResponseEntity.ok(pagination);
    }

    @Override
    public ResponseEntity<EventResponse> get(String id) {
        final EventOutput output = this.eventService.get(id);
        return ResponseEntity.ok(EventApiPresenter.present(output));
    }
}
