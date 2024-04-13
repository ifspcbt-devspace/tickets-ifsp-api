package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.auth.AuthService;
import br.com.ifsp.tickets.app.auth.signin.SignInInputData;
import br.com.ifsp.tickets.app.auth.signin.SignInOutputData;
import br.com.ifsp.tickets.infra.api.AuthAPI;
import br.com.ifsp.tickets.infra.user.models.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.LoginResponse;
import br.com.ifsp.tickets.infra.user.presenters.AuthApiPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController implements AuthAPI {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        final SignInInputData input = SignInInputData.of(request.login(), request.password());
        final SignInOutputData output = this.authService.login(input);
        return ResponseEntity.ok(AuthApiPresenter.present(output));
    }
}
