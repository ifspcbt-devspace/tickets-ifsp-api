package br.com.ifsp.tickets.app.administrative.event.thumbnail.reset;

import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventThumbnail;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import br.com.ifsp.tickets.domain.administrative.user.User;

public class ResetThumbnailUseCase implements IResetThumbnailUseCase {

    private final IFileStorage fileUploader;
    private final IEventGateway eventGateway;

    public ResetThumbnailUseCase(IFileStorage fileUploader, IEventGateway eventGateway) {
        this.fileUploader = fileUploader;
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(ResetThumbnailInput anInput) {
        final User user = anInput.user();
        final EventID eventID = EventID.with(anInput.eventId());
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));

        if ((!user.canManageEvents() && !user.getCompanyID().equals(event.getCompanyID())) && !user.canManageAnyEvent())
            throw new IllegalResourceAccessException("You don't have permission to change this event");

        event.getThumbnail().delete(eventID, fileUploader);
        event.updateThumbnail(EventThumbnail.empty());
        this.eventGateway.update(event);
    }
}
