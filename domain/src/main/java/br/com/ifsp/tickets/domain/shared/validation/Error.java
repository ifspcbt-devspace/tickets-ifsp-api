package br.com.ifsp.tickets.domain.shared.validation;

import java.nio.charset.StandardCharsets;

public record Error(String message) {

    public Error(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        this.message = new String(bytes, StandardCharsets.UTF_8);
    }
}
