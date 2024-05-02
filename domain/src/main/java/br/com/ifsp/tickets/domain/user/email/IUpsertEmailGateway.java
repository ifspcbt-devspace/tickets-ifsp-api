package br.com.ifsp.tickets.domain.user.email;

import br.com.ifsp.tickets.domain.user.UserID;

import java.util.Optional;

public interface IUpsertEmailGateway {

    UpsertEmail create(UpsertEmail upsertEmail);

    UpsertEmail update(UpsertEmail upsertEmail);

    Optional<UpsertEmail> findByUserId(UserID userId);

    Optional<UpsertEmail> findByToken(String token);

    void deleteById(UpsertEmailID upsertEmailID);
}
