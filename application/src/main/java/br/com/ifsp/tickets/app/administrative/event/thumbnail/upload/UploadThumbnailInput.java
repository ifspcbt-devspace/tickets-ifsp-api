package br.com.ifsp.tickets.app.administrative.event.thumbnail.upload;

import br.com.ifsp.tickets.domain.administrative.user.User;

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
