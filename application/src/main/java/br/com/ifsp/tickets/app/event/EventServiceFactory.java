package br.com.ifsp.tickets.app.event;

import br.com.ifsp.tickets.app.event.create.CreateEventUseCase;
import br.com.ifsp.tickets.app.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.GetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.SearchEventUseCase;
import br.com.ifsp.tickets.app.event.sale.create.CreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.create.ICreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.delete.DeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.delete.IDeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.get.GetTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.get.IGetTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.list.IListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.list.ListTicketSaleByEventUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.download.DownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.download.IDownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.reset.IResetThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.reset.ResetThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.upload.IUploadThumbnailUseCase;
import br.com.ifsp.tickets.app.event.thumbnail.upload.UploadThumbnailUseCase;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;

public class EventServiceFactory {

    private static EventService eventService;

    public static EventService create(ICompanyGateway companyGateway, IEventGateway eventGateway, ITicketSaleGateway ticketSaleGateway, IFileStorage fileStorage) {
        if (eventService == null) {
            final ICreateEventUseCase createEventUseCase = new CreateEventUseCase(companyGateway, eventGateway);
            final IGetEventUseCase getEventUseCase = new GetEventUseCase(eventGateway);
            final ISearchEventUseCase searchEventUseCase = new SearchEventUseCase(eventGateway);
            final IDownloadThumbnailUseCase downloadThumbnailUseCase = new DownloadThumbnailUseCase(fileStorage, eventGateway);
            final IUploadThumbnailUseCase uploadThumbnailUseCase = new UploadThumbnailUseCase(fileStorage, eventGateway);
            final IResetThumbnailUseCase resetThumbnailUseCase = new ResetThumbnailUseCase(fileStorage, eventGateway);
            final ICreateTicket2SellUseCase createTicket2SellUseCase = new CreateTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IDeleteTicket2SellUseCase deleteTicket2SellUseCase = new DeleteTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IGetTicket2SellUseCase getTicket2SellUseCase = new GetTicket2SellUseCase(ticketSaleGateway);
            final IListTicketSaleByEventUseCase listTicketSaleByEventUseCase = new ListTicketSaleByEventUseCase(ticketSaleGateway);
            eventService = new EventService(createEventUseCase, getEventUseCase, searchEventUseCase, downloadThumbnailUseCase, uploadThumbnailUseCase, resetThumbnailUseCase, createTicket2SellUseCase, deleteTicket2SellUseCase, getTicket2SellUseCase, listTicketSaleByEventUseCase);
        }
        return eventService;
    }
}
