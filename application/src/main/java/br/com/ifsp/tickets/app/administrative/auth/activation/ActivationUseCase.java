package br.com.ifsp.tickets.app.administrative.auth.activation;

import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.administrative.user.email.UpsertEmail;

public class ActivationUseCase implements IActivationUseCase {

    private final IUserGateway userGateway;
    private final IUpsertEmailGateway upsertEmailGateway;

    public ActivationUseCase(IUserGateway userGateway, IUpsertEmailGateway upsertEmailGateway) {
        this.userGateway = userGateway;
        this.upsertEmailGateway = upsertEmailGateway;
    }

    @Override
    public void execute(ActivationInput anIn) {
        final String token = anIn.token();
        final UpsertEmail upsertEmail = this.upsertEmailGateway.findByToken(token).orElseThrow(() -> NotFoundException.with("Token not found"));
        final User user = upsertEmail.getUser();
        user.enable();
        user.changeEmail(upsertEmail.getEmail());
        this.userGateway.update(user);
        this.upsertEmailGateway.deleteById(upsertEmail.getId());
    }
}
