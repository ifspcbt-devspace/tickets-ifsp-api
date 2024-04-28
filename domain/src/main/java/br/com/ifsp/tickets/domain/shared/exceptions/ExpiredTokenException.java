package br.com.ifsp.tickets.domain.shared.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpiredTokenException extends DomainException {

    public ExpiredTokenException(LocalDateTime dateTime) {
        super("The recovery token has expired at " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dateTime));
    }
}
