package br.com.ifsp.tickets.app.event.thumbnail.reset;

import br.com.ifsp.tickets.domain.user.User;

public record ResetThumbnailInput(
        User user,
        String eventId
) {

    public static ResetThumbnailInput of(User user, String eventId) {
        return new ResetThumbnailInput(user, eventId);
    }
}
