package br.com.ifsp.tickets.infra.contexts.administrative.company.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyJpaEntity, UUID> {

    Optional<CompanyJpaEntity> findByCnpj(String cnpj);

    Page<CompanyJpaEntity> findAll(Specification<CompanyJpaEntity> spec, Pageable pageable);
}
