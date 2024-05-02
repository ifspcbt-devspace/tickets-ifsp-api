package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.app.auth.activation.ActivationInput;
import br.com.ifsp.tickets.app.auth.recovery.change.RecoveryInput;
import br.com.ifsp.tickets.app.auth.recovery.request.RecoveryRequestInput;
import br.com.ifsp.tickets.app.auth.signin.SignInInput;
import br.com.ifsp.tickets.app.auth.signin.SignInOutput;
import br.com.ifsp.tickets.app.auth.signup.SignUpInput;
import br.com.ifsp.tickets.app.auth.signup.SignUpOutput;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.user.email.UpsertEmail;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;
import br.com.ifsp.tickets.domain.user.recovery.PasswordRecovery;
import br.com.ifsp.tickets.infra.config.WebServerConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ContextConfiguration(classes = {WebServerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Autowired
    IUserGateway userGateway;
    @Autowired
    IPasswordRecoveryGateway passwordRecoveryGateway;
    @Autowired
    IUpsertEmailGateway upsertEmailGateway;

    @Test
    @DisplayName("Should register a new user")
    @Order(1)
    void register() {
        final SignUpInput input = SignUpInput.of(
                "Leonardo da Silva",
                "l.6042silva@gmail.com",
                "oleonardosilva",
                "bora2222T",
                LocalDate.of(2006, 10, 18),
                "482.183.980-66",
                "(11) 2025-5373"
        );

        final SignUpOutput output = this.authService.register(input);

        assertThat(output).isNotNull();
        assertThat(output.id()).isNotNull();
    }

    @Test
    @DisplayName("Should activate a user")
    @Order(2)
    void activate() {
        final User user = this.userGateway.findByUsername("oleonardosilva").orElse(null);
        assertThat(user).isNotNull();
        UpsertEmail upsertEmail = this.upsertEmailGateway.findByUserId(user.getId()).orElse(null);
        assertThat(upsertEmail).isNotNull();
        final String token = upsertEmail.getToken();
        final ActivationInput input = ActivationInput.of(token);
        this.authService.activate(input);

        upsertEmail = this.upsertEmailGateway.findByUserId(user.getId()).orElse(null);
        assertThat(upsertEmail).isNull();
    }

    @Test
    @DisplayName("Should login a user")
    @Order(3)
    void login() {
        final SignInInput input = SignInInput.of("oleonardosilva", "bora2222T");
        final SignInOutput output = this.authService.login(input);
        assertThat(output).isNotNull();
        assertThat(output.token()).isNotNull();
        assertThat(output.token()).isNotBlank();
        final SignInOutput.UserOutputData userOutput = output.user();
        assertThat(userOutput).isNotNull();

    }

    @Test
    @DisplayName("Should request password recovery and change password")
    @Order(4)
    void requestRecovery() {
        final RecoveryRequestInput input = RecoveryRequestInput.of("oleonardosilva", "127.0.0.1", "Mozilla/5.0");
        this.authService.requestRecovery(input);
        final User user = this.userGateway.findByUsername("oleonardosilva").orElse(null);
        assertThat(user).isNotNull();
        final boolean existsRequest = this.passwordRecoveryGateway.existsNonExpiredTokenByUser(user);
        assertThat(existsRequest).isTrue();
    }

    @Test
    @DisplayName("Should change password")
    @Order(5)
    void changePassword() {
        final User user = this.userGateway.findByUsername("oleonardosilva").orElse(null);
        assertThat(user).isNotNull();
        final PasswordRecovery passwordRecovery = this.passwordRecoveryGateway.findByUserID(user.getId()).orElse(null);
        assertThat(passwordRecovery).isNotNull();
        final RecoveryInput recInput = RecoveryInput.of(passwordRecovery.getToken(), "novaSenha");
        this.authService.accountRecovery(recInput);
        final boolean existsRequest = this.passwordRecoveryGateway.existsNonExpiredTokenByUser(user);
        assertThat(existsRequest).isFalse();
    }

    @Test
    @DisplayName("Should login a user with a new password")
    @Order(6)
    void loginWithNewPassword() {
        final SignInInput input = SignInInput.of("oleonardosilva", "novaSenha");
        final SignInOutput output = this.authService.login(input);
        assertThat(output).isNotNull();
        assertThat(output.token()).isNotNull();
        assertThat(output.token()).isNotBlank();
        final SignInOutput.UserOutputData userOutput = output.user();
        assertThat(userOutput).isNotNull();
    }


}