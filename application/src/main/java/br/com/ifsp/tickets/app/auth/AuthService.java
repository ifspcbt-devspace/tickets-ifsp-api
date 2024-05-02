package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.activation.ActivationInput;
import br.com.ifsp.tickets.app.auth.activation.IActivationUseCase;
import br.com.ifsp.tickets.app.auth.recovery.change.IRecoveryUseCase;
import br.com.ifsp.tickets.app.auth.recovery.change.RecoveryInput;
import br.com.ifsp.tickets.app.auth.recovery.request.IRecoveryRequestUseCase;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestInput;
import br.com.ifsp.tickets.app.auth.signin.ISignInUseCase;
import br.com.ifsp.tickets.app.auth.signin.SignInInput;
import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.app.auth.signup.ISignUpUseCase;
import br.com.ifsp.tickets.app.auth.signup.SignUpInput;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutput;

public class AuthService {

    private final ISignInUseCase signInUseCase;
    private final ISignUpUseCase signUpUseCase;
    private final IRecoveryRequestUseCase recoveryRequestUseCase;
    private final IRecoveryUseCase recoveryUseCase;
    private final IActivationUseCase activationUseCase;

    public AuthService(ISignInUseCase signInUseCase, ISignUpUseCase signUpUseCase, IRecoveryRequestUseCase recoveryRequestUseCase, IRecoveryUseCase recoveryUseCase, IActivationUseCase activationUseCase) {
        this.signInUseCase = signInUseCase;
        this.signUpUseCase = signUpUseCase;
        this.recoveryRequestUseCase = recoveryRequestUseCase;
        this.recoveryUseCase = recoveryUseCase;
        this.activationUseCase = activationUseCase;
    }

    public void accountRecovery(RecoveryInput inputData) {
        this.recoveryUseCase.execute(inputData);
    }

    public void requestRecovery(RecoveryRequestInput inputData) {
        this.recoveryRequestUseCase.execute(inputData);
    }

    public SignInOutput login(SignInInput inputData) {
        return this.signInUseCase.execute(inputData);
    }

    public SignUpOutput register(SignUpInput inputData) {
        return this.signUpUseCase.execute(inputData);
    }

    public void activate(ActivationInput inputData) {
        this.activationUseCase.execute(inputData);
    }

}
