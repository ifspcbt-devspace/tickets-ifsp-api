package br.com.ifsp.tickets.domain.communication.email;

import br.com.ifsp.tickets.domain.shared.Identifier;

public class EmailID extends Identifier<Long> {

    private final Long value;

    public EmailID(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return this.value;
    }
}
