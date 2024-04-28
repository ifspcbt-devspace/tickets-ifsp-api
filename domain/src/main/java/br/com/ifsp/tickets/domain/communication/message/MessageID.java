package br.com.ifsp.tickets.domain.communication.message;

import br.com.ifsp.tickets.domain.shared.Identifier;

public class MessageID extends Identifier<Integer> {

    private final Integer value;

    public MessageID(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
