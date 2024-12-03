package br.com.ifsp.tickets.app.administrative.event.thumbnail.upload;

import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventThumbnail;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.administrative.user.User;

public class UploadThumbnailUseCase implements IUploadThumbnailUseCase {

    private final IFileStorage fileUploader;
    private final IEventGateway eventGateway;

    public UploadThumbnailUseCase(IFileStorage fileUploader, IEventGateway eventGateway) {
        this.fileUploader = fileUploader;
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(UploadThumbnailInput anInput) {
        final User user = anInput.user();
        final EventID eventID = EventID.with(anInput.eventId());
        final byte[] bytes = anInput.fileContent();
        final String fileName = anInput.fileName();
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));

        if ((!user.canManageEvents() && !user.getCompanyID().equals(event.getCompanyID())) && !user.canManageAnyEvent())
            throw new IllegalResourceAccessException("You don't have permission to change this event");

        if (!IFileStorage.isImageType(fileName))
            throw new IllegalResourceAccessException("You don't have permission to send this type file");

        event.getThumbnail().delete(eventID, fileUploader);
        final EventThumbnail newEventThumbnail = EventThumbnail.upload(fileName, bytes, eventID, fileUploader);
        event.updateThumbnail(newEventThumbnail);
        this.eventGateway.update(event);
    }
}
