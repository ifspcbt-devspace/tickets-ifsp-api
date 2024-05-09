package br.com.ifsp.tickets.domain.communication.email;

import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;

public class EmailID extends Identifier<Long> {

    private final Long value;

    public EmailID(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailID emailID = (EmailID) o;
        return Objects.equals(value, emailID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
