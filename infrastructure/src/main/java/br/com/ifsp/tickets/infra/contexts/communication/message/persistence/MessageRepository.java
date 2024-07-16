package br.com.ifsp.tickets.infra.contexts.communication.message.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageJpaEntity, Integer> {

    Optional<MessageJpaEntity> findBySubjectAndType(int subject, char type);

    boolean existsBySubjectAndType(int subject, char type);
}
