package br.com.ifsp.tickets.app.administrative.enrollment.upsert.retrieve;

import br.com.ifsp.tickets.domain.administrative.enrollment.upsert.IUpsertEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.upsert.UpsertEnrollment;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;

public class GetUpsertEnrollmentUseCase implements IGetUpsertEnrollmentUseCase{
    private final IUpsertEnrollmentGateway upsertEnrollmentGateway;

    public GetUpsertEnrollmentUseCase(IUpsertEnrollmentGateway upsertEnrollmentGateway) {
        this.upsertEnrollmentGateway = upsertEnrollmentGateway;
    }

    @Override
    public GetUpsertEnrollmentOutput execute(GetUpsertEnrollmentInput anIn) {
        UpsertEnrollment upsertEnrollment = this.upsertEnrollmentGateway.getByTicketId(anIn.ticketId()).orElseThrow(() -> NotFoundException.with(UpsertEnrollment.class, TicketID.with(anIn.ticketId())));

        return GetUpsertEnrollmentOutput.from(upsertEnrollment);
    }
}
