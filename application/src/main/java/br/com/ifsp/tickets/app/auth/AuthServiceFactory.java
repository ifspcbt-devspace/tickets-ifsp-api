package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.signin.SignInUseCase;
import br.com.ifsp.tickets.app.auth.signup.SignUpUseCase;
import br.com.ifsp.tickets.domain.user.IUserGateway;

public class AuthServiceFactory {

    private static AuthService authService;

    public static AuthService create(IAuthManager authManager, IAuthUtils authUtils, IUserGateway userGateway) {
        if (authService == null) {
            final SignInUseCase signInUseCase = new SignInUseCase(authUtils, authManager, userGateway);
            final SignUpUseCase signUpUseCase = new SignUpUseCase(userGateway, authUtils, authManager);
            authService = new AuthService(signInUseCase, signUpUseCase);
        }
        return authService;
    }
}
