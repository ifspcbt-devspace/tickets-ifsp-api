package br.com.ifsp.tickets.domain.user;

public interface IPasswordEncoder {

    boolean matches(String aPassword, String aHash);

}
