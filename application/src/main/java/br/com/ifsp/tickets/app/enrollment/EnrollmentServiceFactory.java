package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.app.enrollment.core.create.CreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.core.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.core.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.enrollment.core.retrieve.list.ListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.create.CreateUpsertUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.create.ICreateUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.GetUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.upsert.retrieve.IGetUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.enrollment.upsert.IUpsertEnrollmentGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;

public class EnrollmentServiceFactory {
    private static EnrollmentService enrollmentService;

    public static EnrollmentService create(
            IEmailGateway emailGateway,
            IMessageGateway messageGateway,
            IEventGateway eventGateway,
            IEnrollmentGateway enrollmentGateway,
            ITicketGateway ticketGateway,
            ICompanyGateway companyGateway,
            IFileStorage fileProvider,
            ITicketQRGenerator ticketQRGenerator,
            ITicketSaleGateway ticketSaleGateway,
            IUpsertEnrollmentGateway upsertEnrollmentGateway
    ) {
        if (enrollmentService == null) {
            final ICreateEnrollmentUseCase createEnrollmentUseCase = new CreateEnrollmentUseCase(
                    eventGateway,
                    enrollmentGateway,
                    ticketGateway,
                    ticketSaleGateway,
                    messageGateway,
                    companyGateway,
                    emailGateway,
                    fileProvider,
                    ticketQRGenerator);
            final IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase = new ListEnrollmentsByUserUseCase(
                    enrollmentGateway
            );
            final ICreateUpsertEnrollmentUseCase createUpsertEnrollmentUseCase = new CreateUpsertUpsertEnrollmentUseCase(
                    eventGateway,
                    enrollmentGateway,
                    upsertEnrollmentGateway,
                    ticketGateway,
                    ticketSaleGateway,
                    messageGateway,
                    companyGateway,
                    emailGateway,
                    fileProvider,
                    ticketQRGenerator);
            final IGetUpsertEnrollmentUseCase getUpsertEnrollmentUseCase = new GetUpsertEnrollmentUseCase(upsertEnrollmentGateway);
            enrollmentService = new EnrollmentService(createEnrollmentUseCase, listEnrollmentsByUserUseCase, createUpsertEnrollmentUseCase, getUpsertEnrollmentUseCase);
        }
        return enrollmentService;
    }
}
