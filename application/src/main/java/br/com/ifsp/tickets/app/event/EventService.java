package br.com.ifsp.tickets.app.event;

import br.com.ifsp.tickets.app.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.event.create.CreateEventOutput;
import br.com.ifsp.tickets.app.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.app.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.SearchEventOutput;
import br.com.ifsp.tickets.app.event.sale.create.CreateTicket2SellInput;
import br.com.ifsp.tickets.app.event.sale.create.CreateTicket2SellOutput;
import br.com.ifsp.tickets.app.event.sale.create.ICreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.delete.DeleteTicket2SellInput;
import br.com.ifsp.tickets.app.event.sale.delete.IDeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.app.event.sale.retrieve.get.IGetTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.list.IListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.download.DownloadThumbnailOutput;
import br.com.ifsp.tickets.app.event.thumbnail.download.IDownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.reset.IResetThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.reset.ResetThumbnailInput;
import br.com.ifsp.tickets.app.event.thumbnail.upload.IUploadThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.upload.UploadThumbnailInput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class EventService {

    private final ICreateEventUseCase createEventUseCase;
    private final IGetEventUseCase getEventUseCase;
    private final ISearchEventUseCase searchEventUseCase;
    private final IDownloadThumbnailUseCase downloadThumbnailUseCase;
    private final IUploadThumbnailUseCase uploadThumbnailUseCase;
    private final IResetThumbnailUseCase resetThumbnailUseCase;
    private final ICreateTicket2SellUseCase createTicket2SellUseCase;
    private final IDeleteTicket2SellUseCase deleteTicket2SellUseCase;
    private final IGetTicket2SellUseCase getTicket2SellUseCase;
    private final IListTicketSaleByEventUseCase listTicketSaleByEventUseCase;

    public EventService(ICreateEventUseCase createEventUseCase, IGetEventUseCase getEventUseCase, ISearchEventUseCase searchEventUseCase, IDownloadThumbnailUseCase downloadThumbnailUseCase, IUploadThumbnailUseCase uploadThumbnailUseCase, IResetThumbnailUseCase resetThumbnailUseCase, ICreateTicket2SellUseCase createTicket2SellUseCase, IDeleteTicket2SellUseCase deleteTicket2SellUseCase, IGetTicket2SellUseCase getTicket2SellUseCase, IListTicketSaleByEventUseCase listTicketSaleByEventUseCase) {
        this.createEventUseCase = createEventUseCase;
        this.getEventUseCase = getEventUseCase;
        this.searchEventUseCase = searchEventUseCase;
        this.downloadThumbnailUseCase = downloadThumbnailUseCase;
        this.uploadThumbnailUseCase = uploadThumbnailUseCase;
        this.resetThumbnailUseCase = resetThumbnailUseCase;
        this.createTicket2SellUseCase = createTicket2SellUseCase;
        this.deleteTicket2SellUseCase = deleteTicket2SellUseCase;
        this.getTicket2SellUseCase = getTicket2SellUseCase;
        this.listTicketSaleByEventUseCase = listTicketSaleByEventUseCase;
    }

    public CreateEventOutput create(CreateEventInput input) {
        return this.createEventUseCase.execute(input);
    }

    public EventOutput get(String anIn) {
        return this.getEventUseCase.execute(anIn);
    }

    public Pagination<SearchEventOutput> search(AdvancedSearchQuery anIn) {
        return this.searchEventUseCase.execute(anIn);
    }

    public void uploadThumbnail(UploadThumbnailInput input) {
        this.uploadThumbnailUseCase.execute(input);
    }

    public DownloadThumbnailOutput downloadThumbnail(String id) {
        return this.downloadThumbnailUseCase.execute(id);
    }

    public void resetThumbnail(ResetThumbnailInput input) {
        this.resetThumbnailUseCase.execute(input);
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
