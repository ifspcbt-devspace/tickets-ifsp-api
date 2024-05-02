package br.com.ifsp.tickets.domain.user.email;

import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpsertEmail extends AggregateRoot<UpsertEmailID> {

    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final EmailAddress email;
    private final LocalDateTime requestDate;
    private final User user;
    private final String token;
    private LocalDateTime lastNotificationDate;

    protected UpsertEmail(UpsertEmailID upsertEmailID, EmailAddress email, LocalDateTime requestDate, User user, String token, LocalDateTime lastNotificationDate) {
        super(upsertEmailID);
        this.email = email;
        this.requestDate = requestDate;
        this.user = user;
        this.token = token;
        this.lastNotificationDate = lastNotificationDate;
    }

    private static String generateToken() {
        final StringBuilder token = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            token.append(TOKEN_CHARS.charAt((int) (Math.random() * TOKEN_CHARS.length())));
        }
        return token.toString();
    }

    public static UpsertEmail with(UpsertEmailID upsertEmailID, EmailAddress email, LocalDateTime requestDate, User user, String token, LocalDateTime lastNotificationDate) {
        return new UpsertEmail(upsertEmailID, email, requestDate, user, token, lastNotificationDate);
    }

    public static UpsertEmail create(EmailAddress email, User user) {
        final LocalDateTime now = LocalDateTime.now();
        return new UpsertEmail(UpsertEmailID.unique(), email, now, user, generateToken(), now);
    }

    public void userNotified() {
        this.lastNotificationDate = LocalDateTime.now();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new UpsertEmailValidator(handler, this).validate();
    }
}
