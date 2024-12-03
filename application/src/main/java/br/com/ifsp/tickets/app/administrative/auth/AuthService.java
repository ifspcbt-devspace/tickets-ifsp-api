package br.com.ifsp.tickets.app.administrative.auth;

import br.com.ifsp.tickets.app.administrative.auth.activation.ActivationInput;
import br.com.ifsp.tickets.app.administrative.auth.activation.IActivationUseCase;
import br.com.ifsp.tickets.app.administrative.auth.get.GetUserByIdInput;
import br.com.ifsp.tickets.app.administrative.auth.get.IGetUserByIdUseCase;
import br.com.ifsp.tickets.app.administrative.auth.get.UserOutput;
import br.com.ifsp.tickets.app.administrative.auth.recovery.change.IRecoveryUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.change.RecoveryInput;
import br.com.ifsp.tickets.app.administrative.auth.recovery.request.IRecoveryRequestUseCase;
import br.com.ifsp.tickets.app.administrative.auth.recovery.request.RecoveryRequestInput;
import br.com.ifsp.tickets.app.administrative.auth.signin.ISignInUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signin.SignInInput;
import br.com.ifsp.tickets.app.administrative.auth.signin.SignInOutput;
import br.com.ifsp.tickets.app.administrative.auth.signup.ISignUpUseCase;
import br.com.ifsp.tickets.app.administrative.auth.signup.SignUpInput;
import br.com.ifsp.tickets.app.administrative.auth.signup.SignUpOutput;
import br.com.ifsp.tickets.app.administrative.auth.update.IUpdateUserUseCase;
import br.com.ifsp.tickets.app.administrative.auth.update.UpdateUserInput;

public class AuthService {

    private final ISignInUseCase signInUseCase;
    private final ISignUpUseCase signUpUseCase;
    private final IRecoveryRequestUseCase recoveryRequestUseCase;
    private final IRecoveryUseCase recoveryUseCase;
    private final IActivationUseCase activationUseCase;
    private final IGetUserByIdUseCase getUserByIdUseCase;
    private final IUpdateUserUseCase updateUserUseCase;

    public AuthService(ISignInUseCase signInUseCase, ISignUpUseCase signUpUseCase, IRecoveryRequestUseCase recoveryRequestUseCase, IRecoveryUseCase recoveryUseCase, IActivationUseCase activationUseCase, IGetUserByIdUseCase getUserByIdUseCase, IUpdateUserUseCase updateUserUseCase) {
        this.signInUseCase = signInUseCase;
        this.signUpUseCase = signUpUseCase;
        this.recoveryRequestUseCase = recoveryRequestUseCase;
        this.recoveryUseCase = recoveryUseCase;
        this.activationUseCase = activationUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
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

    public UserOutput update(UpdateUserInput input) {
        return this.updateUserUseCase.execute(input);
    }

    public void activate(ActivationInput inputData) {
        this.activationUseCase.execute(inputData);
    }

    public UserOutput getUserById(GetUserByIdInput input) {
        return this.getUserByIdUseCase.execute(input);
    }

}
