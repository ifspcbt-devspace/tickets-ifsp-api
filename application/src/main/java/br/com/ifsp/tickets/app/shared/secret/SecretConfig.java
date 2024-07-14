package br.com.ifsp.tickets.app.shared.secret;

import io.github.cdimascio.dotenv.Dotenv;

public class SecretConfig {
    private final String BASE_URL;
    private final String API_VERSION;

    public SecretConfig() {
        Dotenv dotenv = Dotenv.load();
        this.BASE_URL = dotenv.get("BASE_URL");
        this.API_VERSION = dotenv.get("API_VERSION");
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAPI_VERSION() {
        return API_VERSION;
    }
}
