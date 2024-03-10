package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidPhoneNumberException extends DomainException {

    public InvalidPhoneNumberException(String phoneNumber) {
        super("Phone number '%s' is not valid".formatted(phoneNumber));
    }

}
