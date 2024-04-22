package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.app.auth.IAuthManager;
import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.Email;

import java.util.Optional;

public class SignInUseCase implements ISignInUseCase {

    private final IAuthUtils authUtils;
    private final IAuthManager authManager;
    private final IUserGateway userGateway;

    public SignInUseCase(IAuthUtils authUtils, IAuthManager authManager, IUserGateway userGateway) {
        this.authUtils = authUtils;
        this.authManager = authManager;
        this.userGateway = userGateway;
    }

    @Override
    public SignInOutputData execute(SignInInputData anIn) {
        final User user = this.userGateway
                .findByUsername(anIn.login())
                .or(() -> {
                    try {
                        return this.userGateway.findByEmail(new Email(anIn.login()));
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                })
                .orElseThrow(() -> NotFoundException.with("User not found with login: " + anIn.login()));

        this.authManager.auth(user.getUsername(), anIn.password());

        final String token = this.authUtils.generateToken(user.getId().toString());

        return SignInOutputData.from(user, token);
    }
}
