package br.com.ifsp.tickets.app.administrative.auth.update;

import br.com.ifsp.tickets.app.administrative.auth.get.UserOutput;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import br.com.ifsp.tickets.domain.administrative.user.vo.RG;

import java.time.LocalDate;

public class UpdateUserUseCase implements IUpdateUserUseCase {

    private final IUserGateway userGateway;

    public UpdateUserUseCase(IUserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UserOutput execute(UpdateUserInput anIn) {
        final User author = anIn.author();
        final UserID target = UserID.with(anIn.id());
        final String name = anIn.name();
        final String bio = anIn.bio();
        final LocalDate birthDate = anIn.birthDate();
        final RG document = new RG(anIn.document());

        final Notification notification = Notification.create("Could not update aggregate User");

        final User targetUser;
        if (author.getId().equals(target)) targetUser = author;
        else if (!author.canManageAnyUsers())
            throw new IllegalResourceAccessException("User does not have permission to manage other users");
        else
            targetUser = this.userGateway.findById(target).orElseThrow(() -> NotFoundException.with(User.class, target));

        targetUser.updateProfile(name, bio, document, birthDate);

        targetUser.validate(notification);
        notification.throwAnyErrors();

        return UserOutput.from(this.userGateway.update(targetUser), author.getId(), author.getRole().getPermissions());
    }
}
