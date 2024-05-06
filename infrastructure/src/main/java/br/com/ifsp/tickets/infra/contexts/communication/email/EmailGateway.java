package br.com.ifsp.tickets.infra.contexts.communication.email;

import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.EmailID;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.infra.contexts.communication.email.persistence.EmailJpaEntity;
import br.com.ifsp.tickets.infra.contexts.communication.email.persistence.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class EmailGateway implements IEmailGateway {

    private final EmailRepository repository;

    @Override
    public Email create(Email anEmail) {
        return this.repository.save(EmailJpaEntity.from(anEmail)).toAggregate();
    }

    @Override
    public Email update(Email anEmail) {
        return this.repository.save(EmailJpaEntity.from(anEmail)).toAggregate();
    }

    @Override
    public Optional<Email> findById(EmailID anID) {
        return this.repository.findById(anID.getValue()).map(EmailJpaEntity::toAggregate);
    }

    @Override
    public List<Email> findNotSent() {
        final PageRequest request = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        return this.repository.findBySentIsFalseAndFailedAttemptsIsLessThan(request, 3).map(EmailJpaEntity::toAggregate).getContent();
    }
}
