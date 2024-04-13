package br.com.ifsp.tickets.infra.user;

import br.com.ifsp.tickets.app.auth.IAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AuthManager implements IAuthManager {

    private final AuthenticationManager authenticationManager;

    @Override
    public void auth(String login, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
    }
}
