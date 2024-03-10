package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidCPFException extends DomainException {

    public InvalidCPFException(String cnpj) {
        super("CPF '%s' is not valid".formatted(cnpj));
    }

}
