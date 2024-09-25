package br.com.ifsp.tickets.domain.shared.file;

import lombok.Getter;

@Getter
public enum FileContextType {

    EVENT("events/"),
    USER("users/"),
    EMAIL("emails/");

    private final String path;

    FileContextType(String path) {
        this.path = path;
    }
}
