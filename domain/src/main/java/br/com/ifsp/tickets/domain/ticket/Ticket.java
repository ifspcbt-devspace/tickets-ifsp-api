package br.com.ifsp.tickets.domain.ticket;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.ChangeTicketStatusException;
import br.com.ifsp.tickets.domain.shared.exceptions.TicketConsumeException;
import br.com.ifsp.tickets.domain.shared.exceptions.TicketExpiredException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.user.UserID;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class Ticket extends Entity<TicketID> {

    private final UserID userID;
    private final EventID eventID;
    private final String description;
    private final Date validIn;
    private final Date expiredIn;
    private final LocalDateTime createdAt;
    private TicketStatus status;
    private TicketCode code;
    private LocalDateTime lastTimeConsumed;

    public Ticket(TicketID ticketID, UserID userID, EventID eventID, String description, TicketStatus status, TicketCode code, Date validIn, Date expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed) {
        super(ticketID);
        this.userID = userID;
        this.eventID = eventID;
        this.description = description;
        this.status = status;
        this.code = code;
        this.validIn = validIn;
        this.expiredIn = expiredIn;
        this.createdAt = createdAt;
        this.lastTimeConsumed = lastTimeConsumed;
    }

    public static Ticket with(TicketID ticketID, UserID userID, EventID eventID, String description, TicketStatus status, TicketCode code, Date validIn, Date expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed) {
        return new Ticket(ticketID, userID, eventID, description, status, code, validIn, expiredIn, createdAt, lastTimeConsumed);
    }

    public static Ticket newTicket(UserID userID, Event event, String description, Date validIn, Date expiredIn) {
        return new Ticket(TicketID.unique(), userID, event.getId(), description, TicketStatus.AVAILABLE, TicketCode.generate(), validIn, expiredIn, LocalDateTime.now(ZoneId.of("GMT-3")), null);
    }

    public void generateNewCode() {
        this.code = TicketCode.generate();
    }

    public void updateStatus(TicketStatus status) {
        this.status = status;
    }

    public void expire() {
        final ZoneId zoneId = ZoneId.of("GMT-3");
        final LocalDateTime now = LocalDateTime.now(zoneId);

        if (this.status.isExpired())
            throw new ChangeTicketStatusException("Ticket is already expired");
        if (now.toLocalDate().isAfter(this.expiredIn.toInstant().atZone(zoneId).toLocalDate())) {
            this.status = TicketStatus.EXPIRED;
        } else throw new ChangeTicketStatusException("Ticket is out of date to be expired, but you can cancel it.");

    }

    public void cancel() {
        if (this.status.isCanceled())
            throw new ChangeTicketStatusException("Ticket is already canceled");
        this.status = TicketStatus.CANCELED;
    }

    public void consume(Event event) {
        if (!event.getStatus().isOpened())
            throw new TicketConsumeException("Event is not opened");
        if (!event.getId().equals(this.eventID))
            throw new TicketConsumeException("Ticket does not belong to this event");
        if (this.status.isCanceled())
            throw new TicketConsumeException("Ticket is canceled");
        if (this.status.isExpired())
            throw new TicketExpiredException(this.getId());

        final ZoneId zoneId = ZoneId.of("GMT-3");
        final LocalDateTime now = LocalDateTime.now(zoneId);

        if (now.toLocalDate().isBefore(this.validIn.toInstant().atZone(zoneId).toLocalDate()))
            throw new TicketConsumeException("Ticket is not valid yet");

        if (now.toLocalDate().isAfter(this.expiredIn.toInstant().atZone(zoneId).toLocalDate()))
            throw new TicketExpiredException(this.getId());

        this.status = TicketStatus.CONSUMED;
        this.lastTimeConsumed = now;
    }

    @Override
    public void validate(IValidationHandler handler) {
        new TicketValidator(handler, this).validate();
    }
}
