package br.com.ifsp.tickets.infra.contexts.event.sale.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
}
