package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidEnumException extends DomainException{

    public InvalidEnumException(Class<? extends Enum<?>> clazz, int code) {
        super("Invalid enum '%s' by code: %s".formatted(clazz.getSimpleName(), code));
    }

}
