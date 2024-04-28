package br.com.ifsp.tickets.infra.communication.message;

import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.MessageID;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import br.com.ifsp.tickets.infra.communication.message.persistence.MessageJpaEntity;
import br.com.ifsp.tickets.infra.communication.message.persistence.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class MessageGateway implements IMessageGateway {

    private final MessageRepository repository;

    @Override
    public Message update(Message message) {
        return this.repository.save(MessageJpaEntity.from(message)).toAggregate();
    }

    @Override
    public Optional<Message> findById(MessageID id) {
        return this.repository.findById(id.getValue()).map(MessageJpaEntity::toAggregate);
    }

    @Override
    public Optional<Message> findBySubjectAndType(MessageSubject subject, MessageType type) {
        return this.repository.findBySubjectAndType(subject.getKey(), type.getKey()).map(MessageJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Message> findAll(SearchQuery query) {
        final PageRequest pageRequest = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final Page<MessageJpaEntity> page = this.repository.findAll(pageRequest);

        return Pagination.of(
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.map(MessageJpaEntity::toAggregate).toList()
        );
    }
}
