package br.com.ifsp.tickets.domain.administrative.enrollment.upsert;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpsertEnrollment extends Entity<UpsertEnrollmentID> {
    private final UserID userID;
    private final String name;
    private final String email;
    private final String document;
    private final LocalDate birthDate;
    private final EventID eventId;
    private final TicketID ticketID;
    private final TicketSaleID ticketSaleId;


    public UpsertEnrollment(UpsertEnrollmentID upsertEnrollmentID, UserID userID, String name, String email, String document, LocalDate birthDate, EventID eventId, TicketID ticketID, TicketSaleID ticketSaleId) {
        super(upsertEnrollmentID);
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.document = document;
        this.birthDate = birthDate;
        this.eventId = eventId;
        this.ticketID = ticketID;
        this.ticketSaleId = ticketSaleId;
    }

    public static UpsertEnrollment with(UpsertEnrollmentID enrollmentID, UserID userID, String name, String email, String document, LocalDate birthDate, EventID eventId, TicketSaleID ticketSaleId, TicketID ticketID) {
        return new UpsertEnrollment(enrollmentID, userID, name, email, document, birthDate, eventId, ticketID, ticketSaleId);
    }

    public static UpsertEnrollment newUpsertEnrollment(String name, String email, String document, LocalDate birthDate, UserID userID, EventID eventID, TicketSaleID ticketSaleId, TicketID ticketID) {
        return new UpsertEnrollment(UpsertEnrollmentID.unique(), userID, name, email, document, birthDate, eventID, ticketID, ticketSaleId);
    }

    @Override
    public void validate(IValidationHandler handler) {

    }
}
