package br.com.ifsp.tickets.app.auth.activation;

public record ActivationInput(String token) {

    public static ActivationInput of(String token) {
        return new ActivationInput(token);
    }
}
