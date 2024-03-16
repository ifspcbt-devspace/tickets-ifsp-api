package br.com.ifsp.tickets.domain.ticket.vo;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.InvalidTicketCodeException;
import lombok.Getter;

import java.security.SecureRandom;

@Getter
public class TicketCode extends ValueObject {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final String code;

    public TicketCode(String code) {
        if (code == null || code.isBlank())
            throw new InvalidTicketCodeException(code);
        if (code.length() != 15)
            throw new InvalidTicketCodeException(code);

        this.code = code;
    }

    public static TicketCode generate() {
        final int size = 15;
        final StringBuilder key = new StringBuilder(size);
        final SecureRandom random = new SecureRandom();

        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            key.append(CHARACTERS.charAt(randomIndex));
        }

        return new TicketCode(key.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketCode that = (TicketCode) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
