package br.com.ifsp.tickets.domain.user.recovery;

import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.exceptions.ArealdyUsedTokenException;
import br.com.ifsp.tickets.domain.shared.exceptions.ExpiredTokenException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PasswordRecovery extends AggregateRoot<PasswordRecoveryID> {

    private static final long SECONDS_TO_EXPIRE = 60 * 60 * 24; // 24 hours
    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final User user;
    private final String ipAddress;
    private final String agent;
    private final String token;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private LocalDateTime usedAt;
    private boolean used;

    public PasswordRecovery(PasswordRecoveryID passwordRecoveryID, User user, String ipAddress, String agent, String token, LocalDateTime createdAt, LocalDateTime usedAt, LocalDateTime expiresAt, boolean used) {
        super(passwordRecoveryID);
        this.user = user;
        this.ipAddress = ipAddress;
        this.agent = agent;
        this.token = token;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    private static String generateToken() {
        final StringBuilder token = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            token.append(TOKEN_CHARS.charAt((int) (Math.random() * TOKEN_CHARS.length())));
        }
        return token.toString();
    }

    public static PasswordRecovery create(User user, String address, String agent) {
        final LocalDateTime createdAt = LocalDateTime.now();
        final LocalDateTime expiresAt = createdAt.plusSeconds(SECONDS_TO_EXPIRE);
        final String token = generateToken();
        return new PasswordRecovery(PasswordRecoveryID.unique(), user, address, agent, token, createdAt, null, expiresAt, false);
    }

    public void use(String password) {
        if (this.used) throw new ArealdyUsedTokenException();
        if (this.expiresAt.isBefore(LocalDateTime.now())) throw new ExpiredTokenException(this.expiresAt);
        this.used = true;
        this.usedAt = LocalDateTime.now();
        this.user.changePassword(password);
    }

    @Override
    public void validate(IValidationHandler handler) {
        new PasswordRecoveryValidator(handler, this).validate();
    }
}
