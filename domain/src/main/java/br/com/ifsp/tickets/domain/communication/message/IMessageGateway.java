package br.com.ifsp.tickets.domain.communication.message;

import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

import java.util.Optional;

public interface IMessageGateway {

    Message update(Message message);

    Optional<Message> findById(MessageID id);

    Optional<Message> findBySubjectAndType(MessageSubject subject, MessageType type);

    Pagination<Message> findAll(SearchQuery query);

}
