package br.com.ifsp.tickets.infra.contexts.user.contexts.upsert.email;

import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.email.IUpsertEmailGateway;
import br.com.ifsp.tickets.domain.user.email.UpsertEmail;
import br.com.ifsp.tickets.domain.user.email.UpsertEmailID;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.infra.contexts.user.contexts.upsert.email.persistence.UpsertEmailJpaEntity;
import br.com.ifsp.tickets.infra.contexts.user.contexts.upsert.email.persistence.UpsertEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class UpsertEmailGateway implements IUpsertEmailGateway {

    private final UpsertEmailRepository repository;

    @Override
    public UpsertEmail create(UpsertEmail upsertEmail) {
        return this.repository.save(UpsertEmailJpaEntity.from(upsertEmail)).toAggregate();
    }

    @Override
    public UpsertEmail update(UpsertEmail upsertEmail) {
        return this.repository.save(UpsertEmailJpaEntity.from(upsertEmail)).toAggregate();
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return this.repository.existsByEmail(email.getValue());
    }

    @Override
    public Optional<UpsertEmail> findByUserId(UserID userId) {
        return this.repository.findByUserId(userId.getValue()).map(UpsertEmailJpaEntity::toAggregate);
    }

    @Override
    public Optional<UpsertEmail> findByToken(String token) {
        return this.repository.findByToken(token).map(UpsertEmailJpaEntity::toAggregate);
    }

    @Override
    public void deleteById(UpsertEmailID upsertEmailID) {
        this.repository.deleteById(upsertEmailID.getValue());
    }
}
