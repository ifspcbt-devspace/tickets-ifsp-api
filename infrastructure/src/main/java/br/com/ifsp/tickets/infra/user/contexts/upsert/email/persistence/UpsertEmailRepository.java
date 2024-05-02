package br.com.ifsp.tickets.infra.user.contexts.upsert.email.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UpsertEmailRepository extends JpaRepository<UpsertEmailJpaEntity, UUID> {

    Optional<UpsertEmailJpaEntity> findByUserId(UUID userId);

    Optional<UpsertEmailJpaEntity> findByToken(String token);
}
