package br.com.ifsp.tickets.infra.contexts.administrative.user;

import br.com.ifsp.tickets.app.administrative.auth.IAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AuthManager implements IAuthManager {

    private final AuthenticationConfiguration config;

    @Override
    public void auth(String login, String password) throws Exception {
        this.config.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login, password));
    }
}
