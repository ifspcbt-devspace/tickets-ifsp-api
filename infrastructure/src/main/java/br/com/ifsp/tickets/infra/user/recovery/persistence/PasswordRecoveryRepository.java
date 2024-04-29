package br.com.ifsp.tickets.infra.user.recovery.persistence;

import br.com.ifsp.tickets.infra.user.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PassRecoveryJpaEntity, UUID> {

    boolean existsByUserAndExpiresAtAfter(UserJpaEntity user, LocalDateTime now);

    void deleteByUserAndExpiresAtAfter(UserJpaEntity user, LocalDateTime now);

    Optional<PassRecoveryJpaEntity> findByToken(String token);
}
