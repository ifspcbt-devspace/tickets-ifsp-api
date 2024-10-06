package br.com.ifsp.tickets.app.auth.get;

import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;

public class GetUserByIdUseCase implements IGetUserByIdUseCase {

    private final IUserGateway userGateway;

    public GetUserByIdUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserOutput execute(GetUserByIdInput anIn) {
        final UserID userID = UserID.with(anIn.id());
        final User user = this.userGateway.findById(userID).orElseThrow(() -> NotFoundException.with(User.class, userID));
        final User authenticatedUser = anIn.authenticatedUser();
        return UserOutput.from(user, userID, authenticatedUser.getRole().getPermissions());
    }
}
