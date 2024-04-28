package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.auth.AuthService;
import br.com.ifsp.tickets.app.auth.AuthServiceFactory;
import br.com.ifsp.tickets.app.auth.IAuthManager;
import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AuthConfig {

    private final IAuthManager authManager;
    private final IAuthUtils authUtils;
    private final IUserGateway userGateway;
    private final IEmailGateway emailGateway;
    private final IMessageGateway messageGateway;
    private final IPasswordRecoveryGateway passwordRecoveryTokenGateway;

    @Bean
    public AuthService authService() {
        return AuthServiceFactory.create(authManager, authUtils, userGateway, emailGateway, messageGateway, passwordRecoveryTokenGateway);
    }

}
