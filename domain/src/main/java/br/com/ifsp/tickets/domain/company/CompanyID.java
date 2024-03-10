package br.com.ifsp.tickets.domain.company;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;

import java.util.UUID;

public class CompanyID extends Identifier<UUID> {

    private final UUID value;

    public CompanyID(UUID value) {
        this.value = value;
    }

    public static CompanyID with(String value) {
        return new CompanyID(UUIDUtils.getFromString(value));
    }

    public static CompanyID with(UUID value) {
        return new CompanyID(value);
    }

    public static CompanyID unique() {
        return new CompanyID(UUID.randomUUID());
    }

    @Override
    public UUID getValue() {
        return this.value;
    }

}
