package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.recovery.request.IRecoveryRequestUseCase;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestCommand;
import br.com.ifsp.tickets.app.auth.signin.ISignInUseCase;
import br.com.ifsp.tickets.app.auth.signin.SignInInputData;
import br.com.ifsp.tickets.app.auth.signin.SignInOutputData;
import br.com.ifsp.tickets.app.auth.signup.ISignUpUseCase;
import br.com.ifsp.tickets.app.auth.signup.SignUpInputData;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutputData;

public class AuthService {

    private final ISignInUseCase signInUseCase;
    private final ISignUpUseCase signUpUseCase;
    private final IRecoveryRequestUseCase recoveryRequestUseCase;

    public AuthService(ISignInUseCase signInUseCase, ISignUpUseCase signUpUseCase, IRecoveryRequestUseCase recoveryRequestUseCase) {
        this.signInUseCase = signInUseCase;
        this.signUpUseCase = signUpUseCase;
        this.recoveryRequestUseCase = recoveryRequestUseCase;
    }

    public void requestRecovery(RecoveryRequestCommand inputData) {
        this.recoveryRequestUseCase.execute(inputData);
    }

    public SignInOutputData login(SignInInputData inputData) {
        return this.signInUseCase.execute(inputData);
    }

    public SignUpOutputData register(SignUpInputData inputData) {
        return this.signUpUseCase.execute(inputData);
    }

}
