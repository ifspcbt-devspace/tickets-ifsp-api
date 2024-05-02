package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalCommandField;

public record SignInInput(
        String login,
        String password
) {

    public SignInInput {
        if (login == null || login.isBlank()) {
            throw new IllegalCommandField("field 'login' cannot be null or empty");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalCommandField("field 'password' cannot be null or empty");
        }
    }

    public static SignInInput of(String login, String password) {
        return new SignInInput(login, password);
    }

}
