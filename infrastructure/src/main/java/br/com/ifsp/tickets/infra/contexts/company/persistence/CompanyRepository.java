package br.com.ifsp.tickets.infra.contexts.company.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyJpaEntity, UUID> {

    Optional<CompanyJpaEntity> findByCnpj(String cnpj);

}
