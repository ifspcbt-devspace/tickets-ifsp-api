package br.com.ifsp.tickets.infra.contexts.event.core.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventJpaEntity, UUID> {

    Page<EventJpaEntity> findAll(Specification<EventJpaEntity> specification, Pageable pageable);

    Page<EventJpaEntity> findAllByCompanyId(UUID companyId, Pageable pageable);
}
