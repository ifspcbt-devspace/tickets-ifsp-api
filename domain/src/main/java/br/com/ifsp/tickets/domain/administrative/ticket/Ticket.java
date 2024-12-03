package br.com.ifsp.tickets.domain.administrative.ticket;

import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.event.TicketConsumedError;
import br.com.ifsp.tickets.domain.shared.event.TicketConsumedSuccess;
import br.com.ifsp.tickets.domain.shared.exceptions.ChangeTicketStatusException;
import br.com.ifsp.tickets.domain.shared.exceptions.DomainException;
import br.com.ifsp.tickets.domain.shared.exceptions.TicketConsumeException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.administrative.ticket.vo.TicketCode;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class Ticket extends Entity<TicketID> {

    private final UserID userID;
    private final String document;
    private final TicketSaleID ticketSaleID;
    private final EventID eventID;
    private final String description;
    private final LocalDate validIn;
    private final LocalDate expiredIn;
    private final LocalDateTime createdAt;
    private TicketStatus status;
    private final TicketCode code;
    private LocalDateTime lastTimeConsumed;

    public Ticket(TicketID ticketID, String document, EventID eventID, TicketSaleID ticketSaleID, String description, TicketStatus status, TicketCode code, LocalDate validIn, LocalDate expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed, UserID userID) {
        super(ticketID);
        this.userID = userID;
        this.document = document;
        this.eventID = eventID;
        this.ticketSaleID = ticketSaleID;
        this.description = description;
        this.status = status;
        this.code = code;
        this.validIn = validIn;
        this.expiredIn = expiredIn;
        this.createdAt = createdAt;
        this.lastTimeConsumed = lastTimeConsumed;
    }

    public static Ticket with(TicketID ticketID, String document, EventID eventID, TicketSaleID ticketSaleID, String description, TicketStatus status, TicketCode code, LocalDate validIn, LocalDate expiredIn, LocalDateTime createdAt, LocalDateTime lastTimeConsumed, UserID userID) {
        return new Ticket(ticketID, document, eventID, ticketSaleID, description, status, code, validIn, expiredIn, createdAt, lastTimeConsumed, userID);
    }

    public static Ticket newTicket(UserID userID, String document, Event event, TicketSale ticketSale, String description, LocalDate validIn, LocalDate expiredIn) {
        return new Ticket(TicketID.unique(), document, event.getId(), ticketSale.getId(), description, TicketStatus.AVAILABLE, TicketCode.generate(), validIn, expiredIn, LocalDateTime.now(), null, userID);
    }

    public static Ticket newTicketWithId(TicketID ticketID, UserID userID, String document, Event event, TicketSale ticketSale, String description, LocalDate validIn, LocalDate expiredIn) {
        return new Ticket(ticketID, document, event.getId(), ticketSale.getId(), description, TicketStatus.AVAILABLE, TicketCode.generate(), validIn, expiredIn, LocalDateTime.now(), null, userID);
    }

    public Optional<UserID> getUserID() {
        return Optional.ofNullable(this.userID);
    }

    private boolean expire() {
        final LocalDateTime now = LocalDateTime.now();

        if (now.toLocalDate().isAfter(this.expiredIn)) {
            this.status = TicketStatus.EXPIRED;
            return true;
        } else return false;
    }

    public boolean isValidToConsume() {
        final LocalDateTime now = LocalDateTime.now();
        return !now.toLocalDate().isBefore(this.validIn);
    }

    public void cancel() {
        if (this.status.isCanceled())
            throw new ChangeTicketStatusException("Ticket is already canceled");
        this.status = TicketStatus.CANCELED;
    }

    public Optional<DomainException> consume(Event event) {
        if (!event.getStatus().isOpened() && !event.getStatus().isInProgress()) {
            final TicketConsumeException exception = new TicketConsumeException("Event is not open and is not in progress");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        if (!event.getId().equals(this.eventID)) {
            final TicketConsumeException exception = new TicketConsumeException("Ticket does not belong to this event");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        if (this.status.isCanceled()) {
            final TicketConsumeException exception = new TicketConsumeException("Ticket is canceled");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        if (this.status.isConsumed()) {
            final TicketConsumeException exception = new TicketConsumeException("Ticket is already consumed");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        if (this.status.isExpired() || this.expire()) {
            final TicketConsumeException exception = new TicketConsumeException("Ticket is expired");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        if (this.isValidToConsume()) {
            final TicketConsumeException exception = new TicketConsumeException("Ticket is not valid yet");
            this.registerEvent(new TicketConsumedError(this, exception.getMessage()));
            return Optional.of(exception);
        }

        this.status = TicketStatus.CONSUMED;
        this.lastTimeConsumed = LocalDateTime.now();
        this.registerEvent(new TicketConsumedSuccess(this));
        return Optional.empty();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new TicketValidator(handler, this).validate();
    }
}
