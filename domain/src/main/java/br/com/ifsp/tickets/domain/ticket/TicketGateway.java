package br.com.ifsp.tickets.domain.ticket;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.user.UserID;

public interface TicketGateway {

    Ticket create(Ticket ticket);

    Ticket findById(TicketID id);

    Ticket findByCode(TicketCode code);

    Pagination<Ticket> findAllByUserID(UserID id);

    Pagination<Ticket> findAllByUserIDAndEventID(UserID userID, EventID eventID);

    Ticket update(Ticket ticket);

    void delete(Ticket id);

    boolean exists(TicketID id);
}
