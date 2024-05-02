package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.recovery.change.RecoveryUseCase;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestUseCase;
import br.com.ifsp.tickets.app.auth.signin.SignInUseCase;
import br.com.ifsp.tickets.app.auth.signup.SignUpUseCase;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;

public class AuthServiceFactory {

    private static AuthService authService;

    public static AuthService create(IAuthManager authManager,
                                     IAuthUtils authUtils,
                                     IUserGateway userGateway,
                                     IEmailGateway emailGateway,
                                     IMessageGateway messageGateway,
                                     IPasswordRecoveryGateway passwordRecoveryTokenGateway) {
        if (authService == null) {
            final SignInUseCase signInUseCase = new SignInUseCase(authUtils, authManager, userGateway);
            final SignUpUseCase signUpUseCase = new SignUpUseCase(userGateway, authUtils, authManager);
            final RecoveryRequestUseCase recoveryRequestUseCase = new RecoveryRequestUseCase(emailGateway, messageGateway, userGateway, passwordRecoveryTokenGateway);
            final RecoveryUseCase recoveryUseCase = new RecoveryUseCase(passwordRecoveryTokenGateway, userGateway);
            authService = new AuthService(signInUseCase, signUpUseCase, recoveryRequestUseCase, recoveryUseCase);
        }
        return authService;
    }
}
