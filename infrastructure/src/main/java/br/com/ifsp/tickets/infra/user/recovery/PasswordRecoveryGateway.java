package br.com.ifsp.tickets.infra.user.recovery;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;
import br.com.ifsp.tickets.domain.user.recovery.PasswordRecovery;
import br.com.ifsp.tickets.infra.user.persistence.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class PasswordRecoveryGateway implements IPasswordRecoveryGateway {

    private final PasswordRecoveryRepository repository;

    @Override
    public PasswordRecovery create(PasswordRecovery passwordRecovery) {
        return this.repository.save(PassRecoveryJpaEntity.from(passwordRecovery)).toAggregate();
    }

    @Override
    public PasswordRecovery update(PasswordRecovery passwordRecovery) {
        return this.repository.save(PassRecoveryJpaEntity.from(passwordRecovery)).toAggregate();
    }

    @Override
    public boolean existsNonExpiredTokenByUser(User user) {
        return this.repository.existsByUserAndExpiresAtAfter(UserJpaEntity.from(user), LocalDateTime.now());
    }

    @Override
    public Optional<PasswordRecovery> findByToken(String token) {
        return this.repository.findByToken(token).map(PassRecoveryJpaEntity::toAggregate);
    }

    @Override
    public void deleteNonExpiredTokenByUser(User user) {
        this.repository.deleteByUserAndExpiresAtAfter(UserJpaEntity.from(user), LocalDateTime.now());
    }
}
