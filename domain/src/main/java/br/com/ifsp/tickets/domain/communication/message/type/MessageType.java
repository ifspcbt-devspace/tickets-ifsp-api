package br.com.ifsp.tickets.domain.communication.message.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageType {

    HTML('H'),
    TEXT('T');

    private final char key;

    MessageType(char key) {
        this.key = key;
    }

    public static MessageType from(char key) {
        return Arrays.stream(values()).filter(type -> type.key == key).findFirst().orElse(null);
    }
}
