package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalErrorException extends NoStacktraceException {

    protected IllegalErrorException(final String aMessage, final Throwable t) {
        super(aMessage, t);
    }

    public static IllegalErrorException with(final String message, final Throwable t) {
        return new IllegalErrorException(message, t);
    }
}
