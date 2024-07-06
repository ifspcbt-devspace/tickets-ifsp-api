package br.com.ifsp.tickets.app.event.sale.retrieve.get;

import br.com.ifsp.tickets.app.event.sale.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
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
