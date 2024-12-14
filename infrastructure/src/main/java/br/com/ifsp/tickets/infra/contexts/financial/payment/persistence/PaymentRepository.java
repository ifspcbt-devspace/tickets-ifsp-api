package br.com.ifsp.tickets.infra.contexts.financial.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {

    Optional<PaymentJpaEntity> findByExternalId(String externalId);
}
