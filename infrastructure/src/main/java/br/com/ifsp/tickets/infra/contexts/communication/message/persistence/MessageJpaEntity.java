package br.com.ifsp.tickets.infra.contexts.communication.message.persistence;

import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.MessageID;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@Getter
public class MessageJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;
    @Column(name = "subject", nullable = false)
    private int subject;
    @Column(name = "type", nullable = false)
    private char type;
    @Column(name = "template", columnDefinition = "TEXT", nullable = false)
    private String template;

    public MessageJpaEntity(Integer id, int subject, char type, String template) {
        this.id = id;
        this.subject = subject;
        this.type = type;
        this.template = template;
    }

    public static MessageJpaEntity from(Message message) {
        return new MessageJpaEntity(
                message.getId().getValue(),
                message.getSubject().getKey(),
                message.getType().getKey(),
                message.getTemplate()
        );
    }

    public Message toAggregate() {
        return new Message(
                new MessageID(id),
                MessageSubject.from(subject),
                template,
                MessageType.from(type)
        );
    }
}
