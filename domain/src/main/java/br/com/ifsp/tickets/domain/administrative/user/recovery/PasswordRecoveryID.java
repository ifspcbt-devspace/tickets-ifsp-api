package br.com.ifsp.tickets.domain.administrative.user.recovery;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class PasswordRecoveryID extends Identifier<UUID> {

    private final UUID uuid;

    public PasswordRecoveryID(UUID uuid) {
        this.uuid = uuid;
    }

    public static PasswordRecoveryID unique() {
        return new PasswordRecoveryID(UUID.randomUUID());
    }

    public static PasswordRecoveryID from(String value) {
        return new PasswordRecoveryID(UUIDUtils.getFromString(value));
    }

    @Override
    public UUID getValue() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordRecoveryID that = (PasswordRecoveryID) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}
