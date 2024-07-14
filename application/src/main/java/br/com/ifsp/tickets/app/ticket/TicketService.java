package br.com.ifsp.tickets.app.ticket;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.app.ticket.check.ICheckTicketUseCase;

public class TicketService {
    private final ICheckTicketUseCase checkTicketUseCase;

    public TicketService(ICheckTicketUseCase checkTicketUseCase) {
        this.checkTicketUseCase = checkTicketUseCase;
    }

    public void checkTicket(CheckTicketInput input) {
        this.checkTicketUseCase.execute(input);
    }
}
