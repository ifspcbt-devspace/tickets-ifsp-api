package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.signin.SignInUseCase;
import br.com.ifsp.tickets.domain.user.IUserGateway;

public class AuthServiceFactory {

    private static AuthService authService;

    public static AuthService create(IAuthManager authManager, IAuthUtils authUtils, IUserGateway userGateway) {
        if (authService == null) {
            authService = new AuthService(new SignInUseCase(authUtils, authManager, userGateway));
        }
        return authService;
    }

}
