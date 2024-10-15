package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.ticket.TicketService;
import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.app.ticket.retrieve.get.GetTicketInput;
import br.com.ifsp.tickets.app.ticket.retrieve.list.ListTicketsByUserInput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.api.TicketAPI;
import br.com.ifsp.tickets.infra.contexts.ticket.models.TicketResponse;
import br.com.ifsp.tickets.infra.contexts.ticket.presenters.TicketApiPresenter;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController implements TicketAPI {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<Void> check(String id) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CheckTicketInput input = CheckTicketInput.of(authenticatedUser.toAggregate(), id);
        this.ticketService.checkTicket(input);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<TicketResponse> get(String id) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final GetTicketInput input = GetTicketInput.of(authenticatedUser.toAggregate(), id);
        final TicketResponse response = TicketApiPresenter.present(this.ticketService.getTicket(input));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Pagination<TicketResponse>> simpleSearch(String id, Integer page, Integer perPage, String terms) {
        final UserJpaEntity authenticatedUser = (UserJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final SearchQuery query = new SearchQuery(page, perPage, terms, "validIn", "desc");
        final Pagination<TicketResponse> response = this.ticketService.listTicketsByUser(ListTicketsByUserInput.of(authenticatedUser.toAggregate(), id, query)).map(TicketApiPresenter::present);
        return ResponseEntity.ok(response);
    }
}
