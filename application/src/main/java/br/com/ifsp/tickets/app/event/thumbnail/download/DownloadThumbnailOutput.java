package br.com.ifsp.tickets.app.event.thumbnail.download;

import java.time.LocalDateTime;

public record DownloadThumbnailOutput(
        String fileName,
        byte[] fileContent,
        LocalDateTime uploadedAt
) {

    public static DownloadThumbnailOutput of(String fileName, byte[] fileContent, LocalDateTime uploadedAt) {
        return new DownloadThumbnailOutput(fileName, fileContent, uploadedAt);
    }
}
