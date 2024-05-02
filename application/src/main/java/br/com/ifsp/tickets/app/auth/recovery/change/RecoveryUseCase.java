package br.com.ifsp.tickets.app.auth.recovery.change;

import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;
import br.com.ifsp.tickets.domain.user.recovery.PasswordRecovery;

public class RecoveryUseCase implements IRecoveryUseCase {

    private final IPasswordRecoveryGateway passwordRecoveryGateway;
    private final IAuthUtils authUtils;
    private final IUserGateway userGateway;

    public RecoveryUseCase(IPasswordRecoveryGateway passwordRecoveryGateway, IAuthUtils authUtils, IUserGateway userGateway) {
        this.passwordRecoveryGateway = passwordRecoveryGateway;
        this.authUtils = authUtils;
        this.userGateway = userGateway;
    }

    @Override
    public void execute(RecoveryInput anIn) {
        final String token = anIn.token();
        final String password = anIn.password();
        final PasswordRecovery passwordRecovery = this.passwordRecoveryGateway.findByToken(token).orElseThrow(() -> NotFoundException.with("Token not found for password recovery"));
        passwordRecovery.use(this.authUtils.encrypt(password));
        this.passwordRecoveryGateway.update(passwordRecovery);
        this.userGateway.update(passwordRecovery.getUser());
    }
}
