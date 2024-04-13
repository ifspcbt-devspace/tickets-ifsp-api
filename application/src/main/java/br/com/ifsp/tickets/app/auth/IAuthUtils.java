package br.com.ifsp.tickets.app.auth;

import br.com.ifsp.tickets.domain.user.User;

import java.util.UUID;

public interface IAuthUtils {

    String encrypt(String aPassword);

    String generateToken(String aSubject);

    boolean isTokenValid(String aToken, User userDetails);

    UUID getUuidFromToken(String aToken);

}
