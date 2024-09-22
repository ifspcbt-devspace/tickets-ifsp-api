package br.com.ifsp.tickets.app.event.thumbnail.upload;

import br.com.ifsp.tickets.domain.user.User;

public record UploadThumbnailInput(
        User user,
        String eventId,
        String fileName,
        byte[] fileContent
) {

    public static UploadThumbnailInput of(User user, String eventId, String fileName, byte[] fileContent) {
        return new UploadThumbnailInput(user, eventId, fileName, fileContent);
    }
}
