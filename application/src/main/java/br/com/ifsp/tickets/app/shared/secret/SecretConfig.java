package br.com.ifsp.tickets.app.shared.secret;

public class SecretConfig {
    private final String BASE_URL;
    private final String API_VERSION;

    public SecretConfig() {
        this.BASE_URL = System.getenv("BASE_URL");
        this.API_VERSION = System.getenv("API_VERSION");
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAPI_VERSION() {
        return API_VERSION;
    }
}
