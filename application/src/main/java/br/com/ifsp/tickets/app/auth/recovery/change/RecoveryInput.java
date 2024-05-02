package br.com.ifsp.tickets.app.auth.recovery.change;

public record RecoveryInput(
        String token,
        String password
) {

    public RecoveryInput {
        if (token == null || token.isBlank())
            throw new IllegalArgumentException("'token' is required");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("'password' is required");
    }

    public static RecoveryInput of(String token, String password) {
        return new RecoveryInput(token, password);
    }

}
