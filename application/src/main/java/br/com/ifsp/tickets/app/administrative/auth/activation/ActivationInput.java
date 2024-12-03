package br.com.ifsp.tickets.app.administrative.auth.activation;

public record ActivationInput(String token) {

    public static ActivationInput of(String token) {
        return new ActivationInput(token);
    }
}
