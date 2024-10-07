package br.com.ifsp.tickets.domain.enrollment.upsert;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class UpsertEnrollmentID extends Identifier<UUID> {
    private final UUID value;

    public UpsertEnrollmentID(UUID value) {
        this.value = value;
    }

    public static UpsertEnrollmentID with(String value) {
        return new UpsertEnrollmentID(UUIDUtils.getFromString(value));
    }

    public static UpsertEnrollmentID with(UUID value) {
        return new UpsertEnrollmentID(value);
    }

    public static UpsertEnrollmentID unique() {
        return new UpsertEnrollmentID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpsertEnrollmentID that = (UpsertEnrollmentID) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
