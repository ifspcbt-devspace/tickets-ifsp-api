package br.com.ifsp.tickets.infra.config.app;


import br.com.ifsp.tickets.app.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.enrollment.EnrollmentServiceFactory;
import br.com.ifsp.tickets.app.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class EnrollmentConfig {

    private final IEmailGateway emailGateway;
    private final IMessageGateway messageGateway;
    private final IEventGateway eventGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final ITicketGateway ticketGateway;
    private final ICompanyGateway companyGateway;
    private final IFileStorage fileProvider;
    private final ITicketQRGenerator ticketQRGenerator;

    @Bean
    public EnrollmentService enrollmentService() {
        return EnrollmentServiceFactory.create(emailGateway, messageGateway, eventGateway, enrollmentGateway, ticketGateway, companyGateway, fileProvider, ticketQRGenerator);
    }
}
