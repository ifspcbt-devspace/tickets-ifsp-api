package br.com.ifsp.tickets.app.administrative.ticket.retrieve.list;

import br.com.ifsp.tickets.app.IUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public interface IListTicketsByUserUseCase extends IUseCase<ListTicketsByUserInput, Pagination<TicketOutput>> {
}
