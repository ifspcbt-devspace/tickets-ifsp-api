package br.com.ifsp.tickets.domain.administrative.user;

import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;
import br.com.ifsp.tickets.domain.shared.Identifier;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier<UUID> {

    private final UUID value;

    public UserID(UUID value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserID userID = (UserID) o;
        return Objects.equals(value, userID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
