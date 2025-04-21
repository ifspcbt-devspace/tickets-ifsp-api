package br.com.ifsp.tickets.app.administrative.auth;

public interface IAuthManager {

    void auth(String login, String password) throws Exception;

}
