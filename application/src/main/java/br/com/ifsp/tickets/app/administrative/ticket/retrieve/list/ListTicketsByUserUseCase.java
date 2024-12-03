package br.com.ifsp.tickets.app.administrative.ticket.retrieve.list;

import br.com.ifsp.tickets.app.administrative.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

public class ListTicketsByUserUseCase implements IListTicketsByUserUseCase {

    private final ITicketGateway ticketGateway;

    public ListTicketsByUserUseCase(ITicketGateway ticketGateway) {
        this.ticketGateway = ticketGateway;
    }

    @Override
    public Pagination<TicketOutput> execute(ListTicketsByUserInput anIn) {
        final User user = anIn.user();
        final UserID userID = UserID.with(anIn.userID());
        final SearchQuery searchQuery = anIn.searchQuery();

        if (!user.canManageAnyUsers() && !user.getId().equals(userID)) {
            throw new IllegalResourceAccessException("You can't access other user's tickets");
        }

        return ticketGateway.findAllByUserID(userID, searchQuery).map(TicketOutput::from);
    }
}
