package br.com.ifsp.tickets.infra.communication.email.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailJpaEntity, Long> {

    Page<EmailJpaEntity> findBySentIsFalseAndFailedAttemptsIsLessThan(Pageable pageable, int maxAttempts);

}
