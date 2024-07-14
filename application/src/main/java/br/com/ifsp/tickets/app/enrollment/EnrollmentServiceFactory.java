package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.app.enrollment.create.CreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.create.ICreateEnrollmentUseCase;
import br.com.ifsp.tickets.app.enrollment.retrieve.list.IListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.app.enrollment.retrieve.list.ListEnrollmentsByUserUseCase;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;

public class EnrollmentServiceFactory {
    private static EnrollmentService enrollmentService;

    public static EnrollmentService create(
            IEmailGateway emailGateway,
            IMessageGateway messageGateway,
            IEventGateway eventGateway,
            IEnrollmentGateway enrollmentGateway,
            ITicketGateway ticketGateway,
            ICompanyGateway companyGateway
    ) {
        if (enrollmentService == null) {
            final ICreateEnrollmentUseCase createEnrollmentUseCase = new CreateEnrollmentUseCase(
                    eventGateway,
                    enrollmentGateway,
                    ticketGateway,
                    messageGateway,
                    companyGateway,
                    emailGateway);
            final IListEnrollmentsByUserUseCase listEnrollmentsByUserUseCase = new ListEnrollmentsByUserUseCase(
                    enrollmentGateway
            );
            enrollmentService = new EnrollmentService(createEnrollmentUseCase, listEnrollmentsByUserUseCase);
        }
        return enrollmentService;
    }
}
