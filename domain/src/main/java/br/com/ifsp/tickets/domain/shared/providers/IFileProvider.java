package br.com.ifsp.tickets.domain.shared.providers;

import br.com.ifsp.tickets.domain.communication.Attachment;

import java.util.Optional;

public interface IFileProvider {

    String uploadEmailAttachment(String attachment, byte[] aContent);

    Optional<Attachment> downloadEmailAttachment(String attachment);

    void deleteEmailAttachment(String attachment);

}
