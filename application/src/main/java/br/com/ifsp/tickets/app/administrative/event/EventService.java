package br.com.ifsp.tickets.app.administrative.event;

import br.com.ifsp.tickets.app.administrative.event.create.CreateEventInput;
import br.com.ifsp.tickets.app.administrative.event.create.CreateEventOutput;
import br.com.ifsp.tickets.app.administrative.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.get.EventOutput;
import br.com.ifsp.tickets.app.administrative.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.administrative.event.retrieve.search.SearchEventOutput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.download.DownloadThumbnailOutput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.download.IDownloadThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.reset.IResetThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.reset.ResetThumbnailInput;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.upload.IUploadThumbnailUseCase;
import br.com.ifsp.tickets.app.administrative.event.thumbnail.upload.UploadThumbnailInput;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class EventService {

    private final ICreateEventUseCase createEventUseCase;
    private final IGetEventUseCase getEventUseCase;
    private final ISearchEventUseCase searchEventUseCase;
    private final IDownloadThumbnailUseCase downloadThumbnailUseCase;
    private final IUploadThumbnailUseCase uploadThumbnailUseCase;
    private final IResetThumbnailUseCase resetThumbnailUseCase;

    public EventService(ICreateEventUseCase createEventUseCase, IGetEventUseCase getEventUseCase, ISearchEventUseCase searchEventUseCase, IDownloadThumbnailUseCase downloadThumbnailUseCase, IUploadThumbnailUseCase uploadThumbnailUseCase, IResetThumbnailUseCase resetThumbnailUseCase) {
        this.createEventUseCase = createEventUseCase;
        this.getEventUseCase = getEventUseCase;
        this.searchEventUseCase = searchEventUseCase;
        this.downloadThumbnailUseCase = downloadThumbnailUseCase;
        this.uploadThumbnailUseCase = uploadThumbnailUseCase;
        this.resetThumbnailUseCase = resetThumbnailUseCase;
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

}
