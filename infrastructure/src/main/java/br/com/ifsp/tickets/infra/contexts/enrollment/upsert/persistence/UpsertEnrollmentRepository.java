package br.com.ifsp.tickets.infra.contexts.enrollment.upsert.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UpsertEnrollmentRepository extends JpaRepository<UpsertEnrollmentJpaEntity, UUID> {
    Optional<UpsertEnrollmentJpaEntity> findByTicketID(UUID uuid);
}
