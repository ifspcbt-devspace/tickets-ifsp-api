package br.com.ifsp.tickets.app.ticket;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.app.ticket.check.ICheckTicketUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.get.GetTicketInput;
import br.com.ifsp.tickets.app.ticket.retrieve.get.IGetTicketByIDUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.get.TicketOutput;

public class TicketService {
    private final ICheckTicketUseCase checkTicketUseCase;
    private final IGetTicketByIDUseCase getTicketByIDUseCase;

    public TicketService(ICheckTicketUseCase checkTicketUseCase, IGetTicketByIDUseCase getTicketByIDUseCase) {
        this.checkTicketUseCase = checkTicketUseCase;
        this.getTicketByIDUseCase = getTicketByIDUseCase;
    }

    public void checkTicket(CheckTicketInput input) {
        this.checkTicketUseCase.execute(input);
    }

    public TicketOutput getTicket(GetTicketInput input) {
        return this.getTicketByIDUseCase.execute(input);
    }
}
