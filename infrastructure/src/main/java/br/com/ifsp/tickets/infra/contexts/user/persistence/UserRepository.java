package br.com.ifsp.tickets.infra.contexts.user.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByUsername(String username);

    Optional<UserJpaEntity> findByEmail(String email);

    @Query("""
                        SELECT u FROM UserJpaEntity u WHERE u.username = :login OR u.email = :login
            """)
    Optional<UserJpaEntity> findByUsernameOrEmail(String login);

    Optional<UserJpaEntity> findByCpf(String cpf);

    Page<UserJpaEntity> findAll(Specification<UserJpaEntity> specification, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCpf(String cpf);
}
