package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalCPFException extends DomainException {

    public IllegalCPFException(String cnpj) {
        super("CPF '%s' is not valid".formatted(cnpj));
    }

}
