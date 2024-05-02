package br.com.ifsp.tickets.infra.api.controllers;

import br.com.ifsp.tickets.app.auth.AuthService;
import br.com.ifsp.tickets.app.auth.activation.ActivationInput;
import br.com.ifsp.tickets.app.auth.recovery.change.RecoveryInput;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestInput;
import br.com.ifsp.tickets.app.auth.signin.SignInInput;
import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.app.auth.signup.SignUpInput;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutput;
import br.com.ifsp.tickets.infra.api.AuthAPI;
import br.com.ifsp.tickets.infra.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterRequest;
import br.com.ifsp.tickets.infra.user.presenters.AuthApiPresenter;
import br.com.ifsp.tickets.infra.user.contexts.recovery.models.RecoveryRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController implements AuthAPI {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        final SignInInput input = SignInInput.of(request.login(), request.password());
        final SignInOutput output = this.authService.login(input);
        return ResponseEntity.ok(AuthApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Void> register(RegisterRequest request) {
        final SignUpInput input = SignUpInput.of(request.name(), request.email(), request.username(), request.password(), request.getBirthDate(), request.cpf(), request.phoneNumber());
        final SignUpOutput output = this.authService.register(input);
        return ResponseEntity.created(URI.create("/v1/users/" + output.id())).build();
    }

    @Override
    public ResponseEntity<Void> activate(String token) {
        final ActivationInput input = ActivationInput.of(token);
        this.authService.activate(input);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> forgotPassword(String login, HttpServletRequest request) {
        final RecoveryRequestInput input = RecoveryRequestInput.of(login, request.getRemoteAddr(), request.getHeader("User-Agent"));
        this.authService.requestRecovery(input);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> accountRecovery(RecoveryRequest request) {
        final RecoveryInput input = RecoveryInput.of(request.token(), request.password());
        this.authService.accountRecovery(input);
        return ResponseEntity.noContent().build();
    }
}
