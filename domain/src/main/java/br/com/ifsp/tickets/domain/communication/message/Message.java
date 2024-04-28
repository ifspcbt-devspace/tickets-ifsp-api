package br.com.ifsp.tickets.domain.communication.message;

import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

@Getter
public class Message extends AggregateRoot<MessageID> {

    private final MessageSubject subject;
    private final MessageType type;
    private String template;

    public Message(MessageID messageID, MessageSubject subject, String template, MessageType type) {
        super(messageID);
        this.subject = subject;
        this.template = template;
        this.type = type;
    }

    public static Message create(MessageSubject subject, String template, MessageType type) {
        return new Message(new MessageID(null), subject, template, type);
    }

    public void updateTemplate(String template) {
        this.template = template;
    }

    @Override
    public void validate(IValidationHandler handler) {
        if (this.subject == null)
            handler.append("'subject' is required");

        if (this.template == null || this.template.isBlank())
            handler.append("'template' is required");

        if (this.type == null)
            handler.append("'type' is required");
    }
}
