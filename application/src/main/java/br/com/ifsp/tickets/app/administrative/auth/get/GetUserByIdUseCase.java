package br.com.ifsp.tickets.app.administrative.auth.get;

import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;

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
