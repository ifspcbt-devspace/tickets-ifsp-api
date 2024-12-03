package br.com.ifsp.tickets.infra.config.app;


import br.com.ifsp.tickets.app.administrative.enrollment.EnrollmentService;
import br.com.ifsp.tickets.app.administrative.enrollment.EnrollmentServiceFactory;
import br.com.ifsp.tickets.app.administrative.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.upsert.IUpsertEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
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
    private final IUpsertEnrollmentGateway upsertEnrollmentGateway;
    private final ITicketGateway ticketGateway;
    private final ICompanyGateway companyGateway;
    private final IFileStorage fileProvider;
    private final ITicketQRGenerator ticketQRGenerator;
    private final ITicketSaleGateway ticketSaleGateway;

    @Bean
    public EnrollmentService enrollmentService() {
        return EnrollmentServiceFactory.create(emailGateway, messageGateway, eventGateway, enrollmentGateway, ticketGateway, companyGateway, fileProvider, ticketQRGenerator, ticketSaleGateway, upsertEnrollmentGateway);
    }
}
