package br.com.ifsp.tickets.app.financial.product;

import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellInput;
import br.com.ifsp.tickets.app.financial.product.create.CreateTicket2SellOutput;
import br.com.ifsp.tickets.app.financial.product.create.ICreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.delete.DeleteTicket2SellInput;
import br.com.ifsp.tickets.app.financial.product.delete.IDeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.app.financial.product.retrieve.get.IGetTicket2SellUseCase;
import br.com.ifsp.tickets.app.financial.product.retrieve.list.IListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class ProductService {

    private final ICreateTicket2SellUseCase createTicket2SellUseCase;
    private final IDeleteTicket2SellUseCase deleteTicket2SellUseCase;
    private final IGetTicket2SellUseCase getTicket2SellUseCase;
    private final IListTicketSaleByEventUseCase listTicketSaleByEventUseCase;

    public ProductService(ICreateTicket2SellUseCase createTicket2SellUseCase, IDeleteTicket2SellUseCase deleteTicket2SellUseCase, IGetTicket2SellUseCase getTicket2SellUseCase, IListTicketSaleByEventUseCase listTicketSaleByEventUseCase) {
        this.createTicket2SellUseCase = createTicket2SellUseCase;
        this.deleteTicket2SellUseCase = deleteTicket2SellUseCase;
        this.getTicket2SellUseCase = getTicket2SellUseCase;
        this.listTicketSaleByEventUseCase = listTicketSaleByEventUseCase;
    }

    public CreateTicket2SellOutput createTicketForSale(CreateTicket2SellInput anIn) {
        return this.createTicket2SellUseCase.execute(anIn);
    }

    public Pagination<Ticket2SellOutput> getTicketSaleByEvent(String anIn) {
        return this.listTicketSaleByEventUseCase.execute(anIn);
    }

    public void deleteTicketForSale(DeleteTicket2SellInput anIn) {
        this.deleteTicket2SellUseCase.execute(anIn);
    }

    public Ticket2SellOutput getTicketForSale(String anIn) {
        return this.getTicket2SellUseCase.execute(anIn);
    }
}
