package br.com.ifsp.tickets.app.ticket;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketInput;
import br.com.ifsp.tickets.app.ticket.check.ICheckTicketUseCase;
import br.com.ifsp.tickets.app.ticket.payment.CheckPaymentInput;
import br.com.ifsp.tickets.app.ticket.payment.ICheckPaymentUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.get.GetTicketInput;
import br.com.ifsp.tickets.app.ticket.retrieve.get.IGetTicketByIDUseCase;
import br.com.ifsp.tickets.app.ticket.retrieve.get.TicketOutput;

public class TicketService {
    private final ICheckTicketUseCase checkTicketUseCase;
    private final IGetTicketByIDUseCase getTicketByIDUseCase;
    private final ICheckPaymentUseCase checkPaymentUseCase;

    public TicketService(ICheckTicketUseCase checkTicketUseCase, IGetTicketByIDUseCase getTicketByIDUseCase, ICheckPaymentUseCase checkPaymentUseCase) {
        this.checkTicketUseCase = checkTicketUseCase;
        this.getTicketByIDUseCase = getTicketByIDUseCase;
        this.checkPaymentUseCase = checkPaymentUseCase;
    }

    public void checkTicket(CheckTicketInput input) {
        this.checkTicketUseCase.execute(input);
    }

    public TicketOutput getTicket(GetTicketInput input) {
        return this.getTicketByIDUseCase.execute(input);
    }

    public void changePaymentStatus(CheckPaymentInput input) {
        this.checkPaymentUseCase.execute(input);
    }
}
