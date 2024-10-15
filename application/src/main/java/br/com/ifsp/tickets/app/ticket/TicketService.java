package br.com.ifsp.tickets.app.ticket;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.app.ticket.check.ICheckTicketUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.app.ticket.retrieve.get.GetTicketInput;
import br.com.ifsp.tickets.app.ticket.retrieve.get.IGetTicketByIDUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.list.IListTicketsByUserUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.list.ListTicketsByUserInput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class TicketService {

    private final ICheckTicketUseCase checkTicketUseCase;
    private final IGetTicketByIDUseCase getTicketByIDUseCase;
    private final IListTicketsByUserUseCase listTicketsByUserUseCase;

    public TicketService(ICheckTicketUseCase checkTicketUseCase, IGetTicketByIDUseCase getTicketByIDUseCase, IListTicketsByUserUseCase listTicketsByUserUseCase) {
        this.checkTicketUseCase = checkTicketUseCase;
        this.getTicketByIDUseCase = getTicketByIDUseCase;
        this.listTicketsByUserUseCase = listTicketsByUserUseCase;
    }

    public void checkTicket(CheckTicketInput input) {
        this.checkTicketUseCase.execute(input);
    }

    public TicketOutput getTicket(GetTicketInput input) {
        return this.getTicketByIDUseCase.execute(input);
    }

    public Pagination<TicketOutput> listTicketsByUser(ListTicketsByUserInput input) {
        return this.listTicketsByUserUseCase.execute(input);
    }
}
