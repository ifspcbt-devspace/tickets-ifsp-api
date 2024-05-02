package br.com.ifsp.tickets.app.auth.signup;

import br.com.ifsp.tickets.domain.user.User;

public record SignUpOutput(
        String id
) {

    public static SignUpOutput from(User user) {
        return new SignUpOutput(user.getId().getValue().toString());
    }
}
