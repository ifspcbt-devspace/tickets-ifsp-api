package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.ticket.TicketService;
import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.infra.api.TicketAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController implements TicketAPI {
    private final TicketService ticketService;

    @Override
    public ResponseEntity<Void> check(String id) {
        final CheckTicketInput input = CheckTicketInput.of(id);
        ticketService.checkTicket(input);
        return ResponseEntity.accepted().build();
    }
}
