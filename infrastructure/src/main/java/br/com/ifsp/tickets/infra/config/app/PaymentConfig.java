package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.administrative.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.app.financial.payment.PaymentService;
import br.com.ifsp.tickets.app.financial.payment.PaymentServiceFactory;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.enrollment.IEnrollmentGateway;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PaymentConfig {

    private final IPaymentGateway paymentGateway;
    private final IOrderGateway orderGateway;
    private final IEventGateway eventGateway;
    private final IEmailGateway emailGateway;
    private final ICompanyGateway companyGateway;
    private final IEnrollmentGateway enrollmentGateway;
    private final IMessageGateway messageGateway;
    private final ITicketGateway ticketGateway;
    private final ITicketQRGenerator ticketQRGenerator;
    private final IDomainEventPublisher eventPublisher;
    private final IFileStorage fileStorage;

    @Bean
    public PaymentService paymentService() {
        return PaymentServiceFactory.create(
                paymentGateway,
                orderGateway,
                eventGateway,
                ticketGateway,
                companyGateway,
                emailGateway,
                enrollmentGateway,
                messageGateway,
                fileStorage,
                ticketQRGenerator,
                eventPublisher
        );
    }
}
