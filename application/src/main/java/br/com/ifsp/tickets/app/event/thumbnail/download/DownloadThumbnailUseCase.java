package br.com.ifsp.tickets.app.event.thumbnail.download;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.EventThumbnail;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;

import java.util.Optional;

public class DownloadThumbnailUseCase implements IDownloadThumbnailUseCase {

    private final IFileStorage fileStorage;
    private final IEventGateway eventGateway;

    public DownloadThumbnailUseCase(IFileStorage fileStorage, IEventGateway eventGateway) {
        this.fileStorage = fileStorage;
        this.eventGateway = eventGateway;
    }

    @Override
    public DownloadThumbnailOutput execute(String anIn) {
        final EventID eventID = EventID.with(anIn);
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        final EventThumbnail thumbnail = event.getThumbnail();
        if (thumbnail.isEmpty()) throw NotFoundException.with("File not found");
        final Optional<byte[]> download = thumbnail.download(eventID, fileStorage);
        if (download.isEmpty()) throw NotFoundException.with("File not found");
        return DownloadThumbnailOutput.of(thumbnail.getFilename(), download.get(), thumbnail.getUploadedAt());
    }
}
