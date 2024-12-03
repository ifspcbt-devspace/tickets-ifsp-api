package br.com.ifsp.tickets.app.administrative.auth.signup;

import br.com.ifsp.tickets.domain.administrative.user.User;

public record SignUpOutput(
        String id
) {

    public static SignUpOutput from(User user) {
        return new SignUpOutput(user.getId().getValue().toString());
    }
}
