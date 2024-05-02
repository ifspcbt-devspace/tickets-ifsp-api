package br.com.ifsp.tickets.domain.user.email;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.UUID;

public class UpsertEmailID extends Identifier<UUID> {

    private final UUID uuid;

    public UpsertEmailID(UUID uuid) {
        this.uuid = uuid;
    }

    public static UpsertEmailID unique() {
        return new UpsertEmailID(UUID.randomUUID());
    }

    public static UpsertEmailID from(String value) {
        return new UpsertEmailID(UUIDUtils.getFromString(value));
    }

    @Override
    public UUID getValue() {
        return this.uuid;
    }
}
