package br.com.ifsp.tickets.domain.shared.file;

import br.com.ifsp.tickets.domain.communication.Attachment;

import java.util.Optional;

public interface IFileStorage {

    static boolean isImageType(String fileName) {
        String extension = getExtension(fileName);
        return extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg");
    }

    static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) {
            extension = fileName.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    boolean uploadFile(FileContextType contextType, String filename, byte[] aContent, String... extraPath);

    Optional<byte[]> downloadFile(FileContextType contextType, String filename, String... extraPath);

    boolean existsFile(FileContextType contextType, String filename, String... extraPath);

    void deleteFile(FileContextType contextType, String filename, String... extraPath);

    String uploadEmailAttachment(String attachment, byte[] aContent);

    Optional<Attachment> downloadEmailAttachment(String attachment);

    void deleteEmailAttachment(String attachment);

}
