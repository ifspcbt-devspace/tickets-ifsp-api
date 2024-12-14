package br.com.ifsp.tickets.infra.contexts.administrative.ticket.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketJpaEntity, UUID> {

    Optional<TicketJpaEntity> findByCodeAndExpiredInBefore(String code, LocalDate date);

    Page<TicketJpaEntity> findAllByEnrollmentUserID(UUID userId, Pageable pageable);

    Page<TicketJpaEntity> findAllByEnrollmentUserIDAndEventId(UUID userId, UUID eventId, Pageable pageable);

}
