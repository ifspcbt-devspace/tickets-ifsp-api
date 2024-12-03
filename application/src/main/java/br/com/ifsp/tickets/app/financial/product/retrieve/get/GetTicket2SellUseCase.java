package br.com.ifsp.tickets.app.financial.product.retrieve.get;

import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.financial.product.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

public class GetTicket2SellUseCase implements IGetTicket2SellUseCase {

    private final ITicketSaleGateway ticketSaleGateway;

    public GetTicket2SellUseCase(ITicketSaleGateway ticketSaleGateway) {
        this.ticketSaleGateway = ticketSaleGateway;
    }

    @Override
    public Ticket2SellOutput execute(String anIn) {
        final TicketSaleID ticketSaleID = TicketSaleID.with(anIn);
        final TicketSale ticketSale = this.ticketSaleGateway.findById(ticketSaleID).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticketSaleID));
        return Ticket2SellOutput.from(ticketSale);
    }
}
