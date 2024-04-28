package br.com.ifsp.tickets.domain.communication;

public record PlaceHolder(String key, String value) {

    public static PlaceHolder of(String key, String value) {
        return new PlaceHolder(key, value);
    }

    public PlaceHolder {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("'key' cannot be null or empty");
        }
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("'value' cannot be null or empty");
        }

        key = "{" + key.trim() + "}";
    }
}
