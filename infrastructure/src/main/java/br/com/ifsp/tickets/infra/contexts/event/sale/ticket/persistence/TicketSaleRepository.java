package br.com.ifsp.tickets.infra.contexts.event.sale.ticket.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketSaleRepository extends JpaRepository<TicketSaleJpaEntity, UUID> {
    Page<TicketSaleJpaEntity> findAllByEventID(UUID eventID, PageRequest request);
    Page<TicketSaleJpaEntity> findAll(Specification<TicketSaleJpaEntity> specification, PageRequest request);
}
