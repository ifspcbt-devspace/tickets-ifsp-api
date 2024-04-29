package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.auth.AuthService;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestCommand;
import br.com.ifsp.tickets.app.auth.signin.SignInInputData;
import br.com.ifsp.tickets.app.auth.signin.SignInOutputData;
import br.com.ifsp.tickets.app.auth.signup.SignUpInputData;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutputData;
import br.com.ifsp.tickets.infra.api.AuthAPI;
import br.com.ifsp.tickets.infra.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterRequest;
import br.com.ifsp.tickets.infra.user.models.register.RegisterResponse;
import br.com.ifsp.tickets.infra.user.presenters.AuthApiPresenter;
import jakarta.servlet.http.HttpServletRequest;
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

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        final SignUpInputData input = SignUpInputData.of(request.name(), request.email(), request.username(), request.password(), request.getBirthDate(), request.cpf(), request.phoneNumber());
        final SignUpOutputData output = this.authService.register(input);
        return ResponseEntity.ok(AuthApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Void> forgotPassword(String login, HttpServletRequest request) {
        final RecoveryRequestCommand command = RecoveryRequestCommand.of(login, request.getRemoteAddr(), request.getHeader("User-Agent"));
        this.authService.requestRecovery(command);
        return ResponseEntity.noContent().build();
    }
}
