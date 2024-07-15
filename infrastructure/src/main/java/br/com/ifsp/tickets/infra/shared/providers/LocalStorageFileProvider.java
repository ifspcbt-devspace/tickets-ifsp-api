package br.com.ifsp.tickets.infra.shared.providers;

import br.com.ifsp.tickets.domain.communication.Attachment;
import br.com.ifsp.tickets.domain.shared.providers.IFileProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
@Slf4j
public class LocalStorageFileProvider implements IFileProvider {

    private final Path rootPath = FileSystems.getDefault().getRootDirectories().iterator().next();
    @Value("${upload-dir}")
    private String uploadDir;

    private Path getUploadDir() {
        final Path path = Paths.get(rootPath + File.separator + uploadDir);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("Não foi possível criar o diretório de upload de arquivos", e);
            }
        }
        return path;
    }

    @Override
    public String uploadEmailAttachment(String attachment, byte[] aContent) {
        try {
            final String name = attachment.split("\\.")[0] + System.currentTimeMillis() + "." + attachment.split("\\.")[1];
            Files.write(this.getUploadDir().resolve(name), aContent);
            return name;
        } catch (IOException e) {
            log.error("Não foi possível salvar o anexo do email", e);
            return null;
        }
    }

    @Override
    public Optional<Attachment> downloadEmailAttachment(String attachment) {
        try {
            return Optional.of(new Attachment(attachment, Files.readAllBytes(this.getUploadDir().resolve(attachment))));
        } catch (IOException e) {
            log.error("Não foi possível baixar o anexo do email", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteEmailAttachment(String attachment) {
        try {
            Files.delete(this.getUploadDir().resolve(attachment));
        } catch (IOException e) {
            log.error("Não foi possível deletar o anexo do email", e);
        }
    }
}
