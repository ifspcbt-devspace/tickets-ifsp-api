package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.Email;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;

public interface IUserGateway {

    User create(User user);

    User findByEmail(Email email);

    User findByUsername(String username);

    User findByCPF(CPF cpf);

    User findById(UserID id);

    User update(User user);

    Pagination<User> findAll(SearchQuery sq);

    void delete(User user);

    boolean exists(UserID id);

    boolean exists(Email email);

    boolean exists(String username);

    boolean exists(PhoneNumber phoneNumber);

}
