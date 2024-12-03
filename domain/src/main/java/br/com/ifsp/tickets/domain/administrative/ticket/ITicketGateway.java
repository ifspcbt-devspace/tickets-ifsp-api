package br.com.ifsp.tickets.domain.administrative.ticket;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.administrative.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

import java.util.Optional;

public interface ITicketGateway {

    Ticket create(Ticket ticket);

    Optional<Ticket> findById(TicketID id);

    Ticket findByCodeAndNotExpired(TicketCode code);

    Pagination<Ticket> findAllByUserID(UserID id, SearchQuery sq);

    Pagination<Ticket> findAllByUserIDAndEventID(UserID userID, EventID eventID, SearchQuery sq);

    Ticket update(Ticket ticket);

    void delete(Ticket id);

    boolean exists(TicketID id);
}
