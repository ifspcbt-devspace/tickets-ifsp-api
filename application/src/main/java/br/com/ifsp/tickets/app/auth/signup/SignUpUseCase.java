package br.com.ifsp.tickets.app.auth.signup;

import br.com.ifsp.tickets.app.auth.IAuthManager;
import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import java.time.LocalDate;

public class SignUpUseCase implements ISignUpUseCase {

    private final IUserGateway userGateway;
    private final IAuthUtils authUtils;
    private final IAuthManager authManager;

    public SignUpUseCase(IUserGateway userGateway, IAuthUtils authUtils, IAuthManager authManager) {
        this.userGateway = userGateway;
        this.authUtils = authUtils;
        this.authManager = authManager;
    }

    @Override
    public SignUpOutputData execute(SignUpInputData anIn) {
        final String name = anIn.name();
        final String username = anIn.username();
        final CPF cpf = new CPF(anIn.cpf());
        final EmailAddress email = new EmailAddress(anIn.email());
        final PhoneNumber phoneNumber = new PhoneNumber(anIn.phoneNumber());
        final LocalDate birthDate = anIn.birthDate();
        final String password = anIn.password();
        final String passwordEncoded = this.authUtils.encrypt(password);

        final User user = User.create(
                name,
                Role.CUSTOMER,
                email,
                phoneNumber,
                username,
                passwordEncoded,
                cpf,
                birthDate
        );

        final Notification notification = Notification.create("Could not create aggregate User");
        user.validate(notification);

        notification
                .throwPossibleErrors()
                .uniqueness(() -> this.userGateway.existsByUsername(username), "username")
                .uniqueness(() -> this.userGateway.existsByEmail(email), "email")
                .uniqueness(() -> this.userGateway.existsByCPF(cpf), "cpf")
                .throwPossibleErrors();

        this.authUtils.validatePassword(password, notification);
        notification.throwPossibleErrors();

        this.userGateway.create(user);
        this.authManager.auth(username, password);

        final String jwtToken = this.authUtils.generateToken(user.getId().getValue().toString());

        return SignUpOutputData.from(jwtToken, user);
    }
}
