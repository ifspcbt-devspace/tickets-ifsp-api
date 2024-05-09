package br.com.ifsp.tickets.domain.communication.message;

import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;

public class MessageID extends Identifier<Integer> {

    private final Integer value;

    public MessageID(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageID messageID = (MessageID) o;
        return Objects.equals(value, messageID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
