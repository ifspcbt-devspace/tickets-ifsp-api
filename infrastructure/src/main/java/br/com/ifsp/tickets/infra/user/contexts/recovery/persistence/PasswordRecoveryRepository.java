package br.com.ifsp.tickets.infra.user.contexts.recovery.persistence;

import br.com.ifsp.tickets.infra.user.persistence.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PassRecoveryJpaEntity, UUID> {

    boolean existsByUserAndExpiresAtAfterAndUsedIsFalse(UserJpaEntity user, LocalDateTime now);

    void deleteByUserAndExpiresAtAfter(UserJpaEntity user, LocalDateTime now);

    Optional<PassRecoveryJpaEntity> findByToken(String token);

    @Query("SELECT pr FROM PassRecoveryJpaEntity pr WHERE pr.user.id = :id")
    Optional<PassRecoveryJpaEntity> findByUserId(UUID id);
}
