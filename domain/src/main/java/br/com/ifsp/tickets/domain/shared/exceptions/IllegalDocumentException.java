package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalDocumentException extends DomainException {

    public IllegalDocumentException(String cnpj) {
        super("Document '%s' is not valid".formatted(cnpj));
    }

}
