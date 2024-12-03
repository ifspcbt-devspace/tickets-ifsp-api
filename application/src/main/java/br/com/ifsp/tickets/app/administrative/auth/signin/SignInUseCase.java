package br.com.ifsp.tickets.app.administrative.auth.signin;

import br.com.ifsp.tickets.app.administrative.auth.IAuthManager;
import br.com.ifsp.tickets.app.administrative.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.utils.ValidationUtils;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.administrative.user.vo.EmailAddress;

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
        if (ValidationUtils.isValidEmail(anIn.login())) {
            final EmailAddress emailAddress = new EmailAddress(anIn.login());
            if (this.upsertEmailGateway.existsByEmail(emailAddress))
                Notification.create("Validation error").append("Activate your account first, check your email").throwAnyErrors();
        }

        final User user = this.userGateway.findByUsernameOrEmail(anIn.login())
                .orElseThrow(() -> NotFoundException.with("User not found with login: " + anIn.login()));

        this.authManager.auth(user.getUsername(), anIn.password());
        final String token = this.authUtils.generateToken(user.getId().toString());
        return SignInOutput.from(user, token);
    }
}
