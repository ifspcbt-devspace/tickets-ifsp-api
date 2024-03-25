package br.com.ifsp.tickets.app.auth;

public interface IAuthService {

    String encrypt(String aPassword);

    String generateToken(String aSubject);
}
