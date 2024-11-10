package br.com.ifsp.tickets.infra.shared;

import br.com.ifsp.tickets.domain.communication.Attachment;
import br.com.ifsp.tickets.domain.shared.file.FileContextType;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Slf4j
public class LocalFileStorage implements IFileStorage {

    @Value("${upload-dir}")
    private String uploadDir;

    private Path getUploadDir() {
        final Path path = Path.of(URI.create(uploadDir));
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("Não foi possível criar o diretório de upload de arquivos", e);
            }
        }
        return path.toAbsolutePath();
    }

    public boolean uploadFile(FileContextType contextType, String filename, byte[] aContent, String... extraPath) {
        try {
            final Path dir = this.getUploadDir();
            final Path path = extraPath.length > 0 ? dir.resolve(contextType.getPath()).resolve(String.join("/", extraPath)) : dir.resolve(contextType.getPath());
            if (Files.notExists(path)) Files.createDirectories(path);
            Files.write(path.resolve(filename), aContent);
            return true;
        } catch (IOException e) {
            log.error("Não foi possível salvar o arquivo", e);
            return false;
        }
    }

    public Optional<byte[]> downloadFile(FileContextType contextType, String filename, String... extraPath) {
        try {
            final Path path = extraPath.length > 0 ? this.getUploadDir().resolve(contextType.getPath()).resolve(String.join("/", extraPath)).resolve(filename) : this.getUploadDir().resolve(contextType.getPath()).resolve(filename);
            return Optional.of(Files.readAllBytes(path));
        } catch (IOException e) {
            log.error("Não foi possível recuperar o arquivo", e);
            return Optional.empty();
        }
    }

    public boolean existsFile(FileContextType contextType, String filename, String... extraPath) {
        final Path path = extraPath.length > 0 ? this.getUploadDir().resolve(contextType.getPath()).resolve(String.join("/", extraPath)).resolve(filename) : this.getUploadDir().resolve(contextType.getPath()).resolve(filename);
        return Files.exists(path);
    }

    public void deleteFile(FileContextType contextType, String filename, String... extraPath) {
        try {
            final Path path = extraPath.length > 0 ? this.getUploadDir().resolve(contextType.getPath()).resolve(String.join("/", extraPath)).resolve(filename) : this.getUploadDir().resolve(contextType.getPath()).resolve(filename);
            Files.delete(path);
        } catch (IOException e) {
            log.error("Não foi possível deletar o arquivo", e);
        }
    }

    @Override
    public String uploadEmailAttachment(String attachment, byte[] aContent) {
        this.uploadFile(FileContextType.EMAIL, attachment, aContent, "attachments");
        return attachment;
    }

    @Override
    public Optional<Attachment> downloadEmailAttachment(String attachment) {
        return this.downloadFile(FileContextType.EMAIL, attachment, "attachments").map(bytes -> new Attachment(attachment, bytes));
    }

    @Override
    public void deleteEmailAttachment(String attachment) {
        this.deleteFile(FileContextType.EMAIL, attachment, "attachments");
    }
}
