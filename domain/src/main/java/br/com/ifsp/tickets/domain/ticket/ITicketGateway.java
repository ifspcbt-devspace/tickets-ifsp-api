package br.com.ifsp.tickets.domain.ticket;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;
import br.com.ifsp.tickets.domain.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.user.UserID;

import javax.swing.text.html.Option;
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

    void checkPayment(TicketID id, PaymentStatus paymentStatus);
}
