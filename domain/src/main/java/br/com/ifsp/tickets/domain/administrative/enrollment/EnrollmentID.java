package br.com.ifsp.tickets.domain.administrative.enrollment;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

public class EnrollmentID extends Identifier<UUID> {

    private final UUID value;

    public EnrollmentID(UUID value) {
        this.value = value;
    }

    public static EnrollmentID with(String value) {
        return new EnrollmentID(UUIDUtils.getFromString(value));
    }

    public static EnrollmentID with(UUID value) {
        return new EnrollmentID(value);
    }

    public static EnrollmentID unique() {
        return new EnrollmentID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrollmentID that = (EnrollmentID) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
