package br.com.ifsp.tickets.infra.contexts.financial.product.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketSaleRepository extends JpaRepository<TicketSaleJpaEntity, UUID> {
    Page<TicketSaleJpaEntity> findAllByEventIdAndActiveIsTrue(UUID eventID, Pageable pageable);
}
