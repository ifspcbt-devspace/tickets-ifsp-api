package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalPhoneNumberException extends DomainException {

    public IllegalPhoneNumberException(String phoneNumber) {
        super("Phone number '%s' is not valid".formatted(phoneNumber));
    }

}
