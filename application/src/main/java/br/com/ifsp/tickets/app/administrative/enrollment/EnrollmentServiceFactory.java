package br.com.ifsp.tickets.app.administrative.enrollment;

import br.com.ifsp.tickets.app.administrative.enrollment.core.create.CreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.core.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.list.ListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.upsert.create.CreateUpsertUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.upsert.create.ICreateUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.upsert.retrieve.GetUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.app.administrative.enrollment.upsert.retrieve.IGetUpsertEnrollmentUseCase;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.upsert.IUpsertEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;

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
