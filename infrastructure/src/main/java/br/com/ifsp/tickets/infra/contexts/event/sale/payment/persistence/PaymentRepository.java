package br.com.ifsp.tickets.infra.contexts.event.sale.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, UUID> {
}
