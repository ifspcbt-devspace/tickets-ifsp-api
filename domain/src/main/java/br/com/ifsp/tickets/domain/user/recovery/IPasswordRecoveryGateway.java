package br.com.ifsp.tickets.domain.user.recovery;

import br.com.ifsp.tickets.domain.user.User;

import java.util.Optional;

public interface IPasswordRecoveryGateway {

    PasswordRecovery create(PasswordRecovery passwordRecovery);

    PasswordRecovery update(PasswordRecovery passwordRecovery);

    boolean existsNonExpiredTokenByUser(User user);

    Optional<PasswordRecovery> findByToken(String token);

    void deleteNonExpiredTokenByUser(User user);

}
