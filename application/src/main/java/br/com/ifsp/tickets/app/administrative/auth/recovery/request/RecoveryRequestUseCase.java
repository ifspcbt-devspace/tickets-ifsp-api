package br.com.ifsp.tickets.app.administrative.auth.recovery.request;

import br.com.ifsp.tickets.domain.communication.PlaceHolder;
import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.administrative.user.IUserGateway;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.recovery.IPasswordRecoveryGateway;
import br.com.ifsp.tickets.domain.administrative.user.recovery.PasswordRecovery;

public class RecoveryRequestUseCase implements IRecoveryRequestUseCase {

    private final IEmailGateway emailGateway;
    private final IMessageGateway messageGateway;
    private final IUserGateway userGateway;
    private final IPasswordRecoveryGateway passwordRecoveryTokenGateway;

    public RecoveryRequestUseCase(IEmailGateway emailGateway, IMessageGateway messageGateway, IUserGateway userGateway, IPasswordRecoveryGateway passwordRecoveryTokenGateway) {
        this.emailGateway = emailGateway;
        this.messageGateway = messageGateway;
        this.userGateway = userGateway;
        this.passwordRecoveryTokenGateway = passwordRecoveryTokenGateway;
    }

    @Override
    public void execute(RecoveryRequestInput aCommand) {
        final String login = aCommand.login();
        final String ipAddress = aCommand.ipAddress();
        final String userAgent = aCommand.userAgent();

        final User user = this.userGateway.findByUsernameOrEmail(login).orElseThrow(() -> NotFoundException.with("User not found with login: " + login));
        if (this.passwordRecoveryTokenGateway.existsNonExpiredTokenByUser(user)) {
            this.passwordRecoveryTokenGateway.deleteNonExpiredTokenByUser(user);
            /*
            todo não invalidar, mas sim retornar um erro de que já foi solicitada a recuperação
             para evitar que um usuário fique recebendo vários e-mails
             */
        }
        final PasswordRecovery passwordRecovery = PasswordRecovery.create(user, ipAddress, userAgent);

        final Notification notification = Notification.create("An error occurred while validating the password recovery request");
        passwordRecovery.validate(notification);
        notification.throwAnyErrors();

        this.passwordRecoveryTokenGateway.create(passwordRecovery);


        final Message message = this.messageGateway
                .findBySubjectAndType(MessageSubject.PASSWORD_RECOVERY, MessageType.HTML)
                .orElseThrow(() -> NotFoundException.with("Message not found with subject: " + MessageSubject.PASSWORD_RECOVERY + " and type: " + MessageType.HTML));

        final Email email = Email.create(user.getEmail().getValue(), message);
        email.validate(notification);
        notification.throwAnyErrors();

        email.with(
                PlaceHolder.of("token", passwordRecovery.getToken()),
                PlaceHolder.of("user_name", user.getName()),
                PlaceHolder.of("user_email", user.getEmail().getValue())
        );

        this.emailGateway.create(email);
    }


}
