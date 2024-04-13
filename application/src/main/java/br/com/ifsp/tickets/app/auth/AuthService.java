package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.signin.ISignInUseCase;
import br.com.ifsp.tickets.app.auth.signin.SignInInputData;
import br.com.ifsp.tickets.app.auth.signin.SignInOutputData;

public class AuthService {

    private final ISignInUseCase signInUseCase;

    public AuthService(ISignInUseCase signInUseCase) {
        this.signInUseCase = signInUseCase;
    }

    public SignInOutputData login(SignInInputData inputData) {
        return this.signInUseCase.execute(inputData);
    }


}
