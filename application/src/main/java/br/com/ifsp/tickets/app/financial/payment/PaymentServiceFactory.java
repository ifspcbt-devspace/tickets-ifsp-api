package br.com.ifsp.tickets.app.financial.payment;

import br.com.ifsp.tickets.app.administrative.enrollment.ITicketQRGenerator;
import br.com.ifsp.tickets.app.financial.payment.handle.HandlePaymentUseCase;
import br.com.ifsp.tickets.app.financial.payment.handle.PaymentSuccessHandler;
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

public class PaymentServiceFactory {

    private static PaymentService paymentService;

    public static PaymentService create(
            IPaymentGateway paymentGateway,
            IOrderGateway orderGateway,
            IEventGateway eventGateway,
            ITicketGateway ticketGateway,
            ICompanyGateway companyGateway,
            IEmailGateway emailGateway,
            IEnrollmentGateway enrollmentGateway,
            IMessageGateway messageGateway,
            IFileStorage fileProvider,
            ITicketQRGenerator ticketQRGenerator,
            IDomainEventPublisher eventPublisher
    ) {
        if (paymentService == null) {
            final PaymentSuccessHandler paymentSuccessHandler = new PaymentSuccessHandler(
                    messageGateway,
                    eventGateway,
                    enrollmentGateway,
                    companyGateway,
                    ticketGateway,
                    emailGateway,
                    fileProvider,
                    ticketQRGenerator,
                    orderGateway,
                    eventPublisher
            );

            final HandlePaymentUseCase paymentUseCase = new HandlePaymentUseCase(paymentGateway, orderGateway, eventPublisher, paymentSuccessHandler);
            paymentService = new PaymentService(paymentUseCase);
        }
        return paymentService;
    }
}
