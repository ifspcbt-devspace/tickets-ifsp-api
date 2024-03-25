package br.com.ifsp.tickets.infra.user;

import br.com.ifsp.tickets.app.auth.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class AuthService implements IAuthService {

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String generateToken(String aSubject) {
        final Instant now = Instant.now();
        final Instant expiresAt = now.plus(3, ChronoUnit.DAYS);
        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("tickets-ifsp-api")
                .subject(aSubject)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public String encrypt(String aPassword) {
        return this.bCryptPasswordEncoder.encode(aPassword);
    }

}
