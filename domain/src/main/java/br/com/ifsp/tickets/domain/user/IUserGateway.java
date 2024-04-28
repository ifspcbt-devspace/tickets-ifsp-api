package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;

import java.util.Optional;

public interface IUserGateway {

    User create(User user);

    Optional<User> findByEmail(EmailAddress email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String login);

    Optional<User> findByCPF(CPF cpf);

    Optional<User> findById(UserID id);

    User update(User user);

    Pagination<User> findAll(AdvancedSearchQuery sq);

    void delete(User user);

    boolean existsById(UserID id);

    boolean existsByEmail(EmailAddress email);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(PhoneNumber phoneNumber);

    boolean existsByCPF(CPF cpf);

}
