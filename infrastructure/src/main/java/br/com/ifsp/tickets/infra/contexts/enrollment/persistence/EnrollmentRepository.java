package br.com.ifsp.tickets.infra.contexts.enrollment.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentJpaEntity, UUID> {
    Page<EnrollmentJpaEntity> findAllByUserID(UUID id, Pageable pageable);
    Page<EnrollmentJpaEntity> findAllByEventID(UUID id, Pageable pageable);
    Optional<EnrollmentJpaEntity> findByUserIDAndEventID(UUID userID, UUID eventID);
    boolean existsByUserIDAndEventID(UUID userID, UUID eventID);
}
