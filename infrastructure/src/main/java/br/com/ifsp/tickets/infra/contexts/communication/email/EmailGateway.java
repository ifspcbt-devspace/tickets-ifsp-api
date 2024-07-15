package br.com.ifsp.tickets.infra.contexts.communication.email;

import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.EmailID;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import br.com.ifsp.tickets.domain.shared.providers.IFileProvider;
import br.com.ifsp.tickets.infra.contexts.communication.email.persistence.EmailJpaEntity;
import br.com.ifsp.tickets.infra.contexts.communication.email.persistence.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class EmailGateway implements IEmailGateway {

    private final JavaMailSender javaMailSender;
    private final IFileProvider fileProvider;
    private final EmailRepository repository;
    @Value("${spring.mail.host}")
    private String emailFrom;

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

    @Override
    public boolean send(Email email) {
        try {
            final MimeMessage mensagem = javaMailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "utf-8");

            helper.setTo(email.getTarget());
            helper.setFrom(this.emailFrom);
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);

            email.getAttachments(fileProvider).forEach(attachment -> {
                try {
                    final InputStreamSource source = new ByteArrayResource(attachment.content());
                    helper.addAttachment(attachment.name(), source);
                } catch (Exception e) {
                    log.warn("Erro ao adicionar anexo ao email %s".formatted(email.getSubject()));
                }
            });

            javaMailSender.send(mensagem);
            email.send();
            this.update(email);
            return true;
        } catch (Exception e) {
            this.fail(email, e);
            return false;
        }
    }

    private void fail(Email email, Exception e) {
        log.warn("Erro ao enviar email para %s do tipo %s".formatted(email.getTarget(), email.getSubject()));
        log.warn("Motivo: %s".formatted(e.getMessage()));
        email.incrementFailedAttempts();
        this.update(email);
    }
}
