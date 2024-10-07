package br.com.ifsp.tickets.infra.contexts.ticket.persistence;

import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.ticket.payment.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketJpaEntity, UUID> {
    Optional<TicketJpaEntity> findByCodeAndExpiredInBefore(String code, java.util.Date date);
    Page<TicketJpaEntity> findAllByUserId(UUID userId, Pageable pageable);
    Page<TicketJpaEntity> findAllByUserIdAndEventId(UUID userId, UUID eventId, Pageable pageable);

    @Modifying
    @Query("UPDATE TicketJpaEntity p SET p.paymentStatus = :status WHERE p.id = :id")
    void updatePaymentStatusById(@Param("id") TicketID id, @Param("status") PaymentStatus status);
}
