package br.com.ifsp.tickets.app.financial.product;

import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.create.ICreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.delete.DeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.delete.IDeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.get.GetTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.get.IGetTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.list.IListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.list.ListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;

public class ProductServiceFactory {

    private static ProductService productService;

    public static ProductService create(IEventGateway eventGateway, ITicketSaleGateway ticketSaleGateway) {
        if (productService == null) {
            final ICreateTicket2SellUseCase createTicket2SellUseCase = new CreateTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IDeleteTicket2SellUseCase deleteTicket2SellUseCase = new DeleteTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IGetTicket2SellUseCase getTicket2SellUseCase = new GetTicket2SellUseCase(ticketSaleGateway);
            final IListTicketSaleByEventUseCase listTicketSaleByEventUseCase = new ListTicketSaleByEventUseCase(ticketSaleGateway);
            productService = new ProductService(createTicket2SellUseCase, deleteTicket2SellUseCase, getTicket2SellUseCase, listTicketSaleByEventUseCase);
        }
        return productService;
    }
}
