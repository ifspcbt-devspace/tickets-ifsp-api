package br.com.ifsp.tickets.app.administrative.auth;

import br.com.ifsp.tickets.app.administrative.auth.activation.ActivationUseCase;
import br.com.ifsp.tickets.app.administrative.auth.activation.IActivationUseCase;
import br.com.ifsp.tickets.app.administrative.auth.get.GetUserByIdUseCase;
import br.com.ifsp.tickets.app.administrative.auth.get.IGetUserByIdUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.change.IRecoveryUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.change.RecoveryUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.request.IRecoveryRequestUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.request.RecoveryRequestUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signin.ISignInUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signin.SignInUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signup.ISignUpUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signup.SignUpUseCase;
import br.com.ifsp.tickets.app.administrative.auth.update.IUpdateUserUseCase;
import br.com.ifsp.tickets.app.administrative.auth.update.UpdateUserUseCase;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.administrative.user.recovery.IPasswordRecoveryGateway;

public class AuthServiceFactory {

    private static AuthService authService;

    public static AuthService create(IAuthManager authManager,
                                     IAuthUtils authUtils,
                                     IUserGateway userGateway,
                                     IEmailGateway emailGateway,
                                     IMessageGateway messageGateway,
                                     IPasswordRecoveryGateway passwordRecoveryTokenGateway,
                                     IUpsertEmailGateway upsertEmailGateway) {
        if (authService == null) {
            final ISignInUseCase signInUseCase = new SignInUseCase(authUtils, authManager, userGateway, upsertEmailGateway);
            final ISignUpUseCase signUpUseCase = new SignUpUseCase(userGateway, authUtils, upsertEmailGateway, messageGateway, emailGateway);
            final IRecoveryRequestUseCase recoveryRequestUseCase = new RecoveryRequestUseCase(emailGateway, messageGateway, userGateway, passwordRecoveryTokenGateway);
            final IRecoveryUseCase recoveryUseCase = new RecoveryUseCase(passwordRecoveryTokenGateway, authUtils, userGateway);
            final IActivationUseCase activationUseCase = new ActivationUseCase(userGateway, upsertEmailGateway);
            final IGetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(userGateway);
            final IUpdateUserUseCase updateUserUseCase = new UpdateUserUseCase(userGateway);

            authService = new AuthService(
                    signInUseCase,
                    signUpUseCase,
                    recoveryRequestUseCase,
                    recoveryUseCase,
                    activationUseCase,
                    getUserByIdUseCase,
                    updateUserUseCase
            );
        }
        return authService;
    }
}
