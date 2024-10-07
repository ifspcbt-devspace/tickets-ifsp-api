package br.com.ifsp.tickets.app.ticket.payment;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;

public class CheckPaymentUseCase implements ICheckPaymentUseCase {

    private final ITicketGateway ticketGateway;

    public CheckPaymentUseCase(ITicketGateway ticketGateway) {
        this.ticketGateway = ticketGateway;
    }

    @Override
    public void execute(CheckPaymentInput anIn) {
        final PaymentStatus status = PaymentStatus.valueOf(anIn.status());
        this.ticketGateway.checkPayment(TicketID.with(anIn.id()), status);
    }
}
