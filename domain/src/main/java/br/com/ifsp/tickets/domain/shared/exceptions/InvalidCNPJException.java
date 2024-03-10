package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidCNPJException extends DomainException {

    public InvalidCNPJException(String cnpj) {
        super("CNPJ '%s' is not valid".formatted(cnpj));
    }

}
