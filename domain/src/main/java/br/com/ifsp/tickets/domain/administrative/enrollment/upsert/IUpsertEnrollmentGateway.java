package br.com.ifsp.tickets.domain.administrative.enrollment.upsert;


import java.util.Optional;

public interface IUpsertEnrollmentGateway {
    UpsertEnrollment create(UpsertEnrollment enrollment);
    Optional<UpsertEnrollment> getByTicketId(String ticketId);
}
