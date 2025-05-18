package br.com.ifsp.tickets.infra.contexts.administrative.user;

import br.com.ifsp.tickets.app.administrative.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.shared.validation.Error;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.passay.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class AuthUtils implements IAuthUtils {

    private static final CharacterRule LOWERCASE = new CharacterRule(EnglishCharacterData.LowerCase, 1);
    private static final CharacterRule UPPERCASE = new CharacterRule(EnglishCharacterData.UpperCase, 1);
    private static final CharacterRule NUMBER = new CharacterRule(EnglishCharacterData.Digit, 1);
    private static final LengthRule LENGTH_RULE = new LengthRule(6, 16);

    private final PasswordValidator passwordValidator = new PasswordValidator(LENGTH_RULE, NUMBER, UPPERCASE, LOWERCASE);
    private final String secretKey;
    private final String issuer;
    private final PasswordEncoder passwordEncoder;

    public AuthUtils(PasswordEncoder passwordEncoder, String secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
        this.issuer = "tickets-ifsp-api";
    }

    public String generateToken(String aSubject) {
        return JWT
                .create()
                .withIssuer(issuer)
                .withSubject(aSubject)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .sign(this.getSingInKey());
    }

    public boolean isTokenValid(String token, User userDetails) {
        final UUID uuid = this.getUuidFromToken(token);
        if (userDetails.getPasswordDate() != null && userDetails.getPasswordDate().isAfter(extractIssuedAt(token)))
            return false;
        return uuid.equals(userDetails.getId().getValue()) && !isTokenExpired(token);
    }

    public UUID getUuidFromToken(String token) {
        return UUID.fromString(this.decode(token).getSubject());
    }

    @Override
    public String encrypt(String aPassword) {
        return this.passwordEncoder.encode(aPassword);
    }

    @Override
    public void validatePassword(String aPassword, IValidationHandler validationHandler) {
        final PasswordData passwordData = new PasswordData(aPassword);
        final RuleResult result = passwordValidator.validate(passwordData);
        passwordValidator.getMessages(result).forEach(message -> validationHandler.append(new Error(message)));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = this.extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return decode(token).getExpiresAt();
    }

    private LocalDate extractIssuedAt(String token) {
        final Instant issuedAt = decode(token).getIssuedAt().toInstant();
        return issuedAt.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private DecodedJWT decode(String token) {
        return JWT.require(this.getSingInKey())
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    private Algorithm getSingInKey() {
        return Algorithm.HMAC256(secretKey);
    }


}
