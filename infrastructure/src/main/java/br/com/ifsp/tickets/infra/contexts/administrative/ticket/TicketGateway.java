package br.com.ifsp.tickets.infra.contexts.administrative.ticket;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;
import br.com.ifsp.tickets.domain.administrative.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import br.com.ifsp.tickets.infra.contexts.administrative.ticket.persistence.TicketJpaEntity;
import br.com.ifsp.tickets.infra.contexts.administrative.ticket.persistence.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class TicketGateway implements ITicketGateway {
    private final TicketRepository repository;

    @Override
    public Ticket create(Ticket ticket) {
        return this.repository.save(TicketJpaEntity.from(ticket)).toAggregate();
    }

    @Override
    public Optional<Ticket> findById(TicketID id) {
        return this.repository.findById(id.getValue()).map(TicketJpaEntity::toAggregate);
    }

    @Override
    public Ticket findByCodeAndNotExpired(TicketCode code) {
        return this.repository.findByCodeAndExpiredInBefore(code.getCode(), new java.util.Date())
                .map(TicketJpaEntity::toAggregate).orElse(null);
    }

    @Override
    public Pagination<Ticket> findAllByUserID(UserID id, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<Ticket> page = this.repository.findAllByUserId(id.getValue(), request).map(TicketJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Pagination<Ticket> findAllByUserIDAndEventID(UserID userID, EventID eventID, SearchQuery sq) {
        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<Ticket> page = this.repository.findAllByUserIdAndEventId(userID.getValue(), eventID.getValue(), request)
                .map(TicketJpaEntity::toAggregate);

        return Pagination.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent()
        );
    }

    @Override
    public Ticket update(Ticket ticket) {
        return this.repository.save(TicketJpaEntity.from(ticket)).toAggregate();
    }

    @Override
    public void delete(Ticket id) {
        this.repository.deleteById(id.getId().getValue());
    }

    @Override
    public boolean exists(TicketID id) {
        return this.repository.existsById(id.getValue());
    }
}
