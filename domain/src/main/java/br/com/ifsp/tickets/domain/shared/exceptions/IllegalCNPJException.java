package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalCNPJException extends DomainException {

    public IllegalCNPJException(String cnpj) {
        super("CNPJ '%s' is not valid".formatted(cnpj));
    }

}
