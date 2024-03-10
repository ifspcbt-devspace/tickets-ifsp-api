package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;
import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.UUID;

public class UserID extends Identifier<UUID> {

    private final UUID value;

    protected UserID(UUID value) {
        this.value = value;
    }

    public static UserID with(String value) {
        return new UserID(UUIDUtils.getFromString(value));
    }

    public static UserID with(UUID value) {
        return new UserID(value);
    }

    public static UserID unique() {
        return new UserID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }
}
