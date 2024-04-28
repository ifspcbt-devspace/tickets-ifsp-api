package br.com.ifsp.tickets.domain.communication;

public record Attachment(String name, byte[] content) {

    public static Attachment of(String name, byte[] content) {
        return new Attachment(name, content);
    }

}
