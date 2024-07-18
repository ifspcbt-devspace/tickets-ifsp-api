package br.com.ifsp.tickets.app.auth.signin;

import br.com.ifsp.tickets.app.auth.IAuthManager;
import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;

public class SignInUseCase implements ISignInUseCase {

    private final IAuthUtils authUtils;
    private final IAuthManager authManager;
    private final IUserGateway userGateway;
    private final IUpsertEmailGateway upsertEmailGateway;

    public SignInUseCase(IAuthUtils authUtils, IAuthManager authManager, IUserGateway userGateway, IUpsertEmailGateway upsertEmailGateway) {
        this.authUtils = authUtils;
        this.authManager = authManager;
        this.userGateway = userGateway;
        this.upsertEmailGateway = upsertEmailGateway;
    }

    @Override
    public SignInOutput execute(SignInInput anIn) {
        EmailAddress emailAddress = null;
        try {
            emailAddress = new EmailAddress(anIn.login());
        } catch (Exception ignored) {
        }
        if (emailAddress != null && this.upsertEmailGateway.existsByEmail(emailAddress))
            Notification.create("Validation error").append("Activate your account first, check your email").throwPossibleErrors();

        final User user = this.userGateway.findByUsernameOrEmail(anIn.login())
                .orElseThrow(() -> NotFoundException.with("User not found with login: " + anIn.login()));

        this.authManager.auth(user.getUsername(), anIn.password());
        final String token = this.authUtils.generateToken(user.getId().toString());
        return SignInOutput.from(user, token);
    }
}
