package br.com.ifsp.tickets.domain.user.email;

import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;

import java.util.Optional;

public interface IUpsertEmailGateway {

    UpsertEmail create(UpsertEmail upsertEmail);

    UpsertEmail update(UpsertEmail upsertEmail);

    boolean existsByEmail(EmailAddress email);

    Optional<UpsertEmail> findByUserId(UserID userId);

    Optional<UpsertEmail> findByToken(String token);

    void deleteById(UpsertEmailID upsertEmailID);
}
