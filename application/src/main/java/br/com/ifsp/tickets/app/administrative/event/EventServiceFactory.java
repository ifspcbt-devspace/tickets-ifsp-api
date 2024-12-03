package br.com.ifsp.tickets.app.administrative.event;

import br.com.ifsp.tickets.app.administrative.event.create.CreateEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.get.GetEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.search.SearchEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.download.DownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.download.IDownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.reset.IResetThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.reset.ResetThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.upload.IUploadThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.upload.UploadThumbnailUseCase;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;

public class EventServiceFactory {

    private static EventService eventService;

    public static EventService create(ICompanyGateway companyGateway, IEventGateway eventGateway, IFileStorage fileStorage, IDomainEventPublisher eventPublisher) {
        if (eventService == null) {
            final ICreateEventUseCase createEventUseCase = new CreateEventUseCase(companyGateway, eventGateway, eventPublisher);
            final IGetEventUseCase getEventUseCase = new GetEventUseCase(eventGateway);
            final ISearchEventUseCase searchEventUseCase = new SearchEventUseCase(eventGateway);
            final IDownloadThumbnailUseCase downloadThumbnailUseCase = new DownloadThumbnailUseCase(fileStorage, eventGateway);
            final IUploadThumbnailUseCase uploadThumbnailUseCase = new UploadThumbnailUseCase(fileStorage, eventGateway);
            final IResetThumbnailUseCase resetThumbnailUseCase = new ResetThumbnailUseCase(fileStorage, eventGateway);
            eventService = new EventService(createEventUseCase, getEventUseCase, searchEventUseCase, downloadThumbnailUseCase, uploadThumbnailUseCase, resetThumbnailUseCase);
        }
        return eventService;
    }
}
