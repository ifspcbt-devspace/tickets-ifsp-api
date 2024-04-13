package br.com.ifsp.tickets.infra.user;

import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.shared.utils.UUIDUtils;
import br.com.ifsp.tickets.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

public class AuthUtils implements IAuthUtils {

    private final String secretKey;
    private final PasswordEncoder passwordEncoder;

    public AuthUtils(PasswordEncoder passwordEncoder, String secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    public String generateToken(String aSubject) {
        return Jwts
                .builder()
                .setIssuer("tickets-ifsp-api")
                .setSubject(aSubject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(14, ChronoUnit.DAYS)))
                .signWith(this.getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, User userDetails) {
        final UUID uuid = this.getUuidFromToken(token);
        if (userDetails.getPasswordDate() != null && userDetails.getPasswordDate().after(extractIssuedAt(token)))
            return false;
        return uuid.equals(userDetails.getId().getValue()) && !isTokenExpired(token);
    }

    public UUID getUuidFromToken(String token) {
        return UUIDUtils.getFromString(this.extractClaim(token, Claims::getSubject));
    }

    @Override
    public String encrypt(String aPassword) {
        return this.passwordEncoder.encode(aPassword);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(this.extractAllClaims(token));
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token) != null
                && this.extractExpiration(token).before(Date.from(Instant.now()));
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Date extractIssuedAt(String token) {
        return extractAllClaims(token).getIssuedAt();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSingInKey() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
