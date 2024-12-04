package br.com.ifsp.tickets.infra.contexts.financial.order.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    Optional<OrderJpaEntity> findByIdAndDocument(Long id, String document);

    Page<OrderJpaEntity> findAll(Specification<OrderJpaEntity> spec, Pageable pageable);

}
