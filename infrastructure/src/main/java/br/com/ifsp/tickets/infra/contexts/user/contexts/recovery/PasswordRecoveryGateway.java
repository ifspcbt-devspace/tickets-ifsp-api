package br.com.ifsp.tickets.infra.contexts.user.contexts.recovery;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.recovery.IPasswordRecoveryGateway;
import br.com.ifsp.tickets.domain.user.recovery.PasswordRecovery;
import br.com.ifsp.tickets.infra.contexts.user.contexts.recovery.persistence.PassRecoveryJpaEntity;
import br.com.ifsp.tickets.infra.contexts.user.contexts.recovery.persistence.PasswordRecoveryRepository;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
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
        return this.repository.existsByUserAndExpiresAtAfterAndUsedIsFalse(UserJpaEntity.from(user), LocalDateTime.now());
    }

    @Override
    public Optional<PasswordRecovery> findByToken(String token) {
        return this.repository.findByToken(token).map(PassRecoveryJpaEntity::toAggregate);
    }

    @Override
    public Optional<PasswordRecovery> findByUserID(UserID userID) {
        return this.repository.findByUserId(userID.getValue()).map(PassRecoveryJpaEntity::toAggregate);
    }

    @Override
    public void deleteNonExpiredTokenByUser(User user) {
        this.repository.deleteByUserAndExpiresAtAfter(UserJpaEntity.from(user), LocalDateTime.now());
    }
}
