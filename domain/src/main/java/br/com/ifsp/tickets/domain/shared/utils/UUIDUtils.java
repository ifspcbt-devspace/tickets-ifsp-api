package br.com.ifsp.tickets.domain.shared.utils;

import br.com.ifsp.tickets.domain.shared.exceptions.DomainException;

import java.util.UUID;

public class UUIDUtils {

    public static UUID getFromString(String uuidStr) {
        final UUID uuid;
        try {
            uuid = UUID.fromString(uuidStr);
        } catch (Exception ex) {
            throw DomainException.with("UUID '%s' is invalid".formatted(uuidStr));
        }
        return uuid;
    }

    public static boolean isValidUUID(String uuid) {
        try {
            getFromString(uuid);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
