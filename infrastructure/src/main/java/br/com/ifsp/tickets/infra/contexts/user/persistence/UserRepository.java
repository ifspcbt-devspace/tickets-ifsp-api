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

    @Query("""
                                    SELECT u FROM UserJpaEntity u WHERE u.email ilike :email
            """
    )
    Optional<UserJpaEntity> findByEmail(String email);

    @Query("""
                        SELECT u FROM UserJpaEntity u WHERE u.username = :login OR u.email ilike :login
            """)
    Optional<UserJpaEntity> findByUsernameOrEmail(String login);

    Optional<UserJpaEntity> findByEncryptedDocument(String document);

    Page<UserJpaEntity> findAll(Specification<UserJpaEntity> specification, Pageable pageable);

    @Query("""
                        SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserJpaEntity u WHERE u.email ilike :email
            """
    )
    boolean existsByEmail(String email);

    @Query("""
                        SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserJpaEntity u WHERE u.username ilike :username
            """
    )
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEncryptedDocument(String document);
}
