package br.com.ifsp.tickets.app.administrative.enrollment.upsert.retrieve;

import br.com.ifsp.tickets.domain.administrative.enrollment.upsert.UpsertEnrollment;

import java.time.LocalDate;

public record GetUpsertEnrollmentOutput(
        String userID,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId,
        String ticketID,
        String ticketSaleId
) {

    public static GetUpsertEnrollmentOutput from(UpsertEnrollment output) {
        return new GetUpsertEnrollmentOutput(
                output.getUserID().getValue().toString(),
                output.getName(),
                output.getEmail(),
                output.getDocument(),
                output.getBirthDate(),
                output.getEventId().getValue().toString(),
                output.getTicketID().getValue().toString(),
                output.getTicketSaleId().getValue().toString()
        );
    }
}
