package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.app.auth.IAuthManager;
import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;

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
    public SignInOutput execute(SignInInput anIn) {
        final User user = this.userGateway.findByUsernameOrEmail(anIn.login())
                .orElseThrow(() -> NotFoundException.with("User not found with login: " + anIn.login()));

        this.authManager.auth(user.getUsername(), anIn.password());
        final String token = this.authUtils.generateToken(user.getId().toString());
        return SignInOutput.from(user, token);
    }
}
