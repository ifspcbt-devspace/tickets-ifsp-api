package br.com.ifsp.tickets.infra.jobs;

import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.communication.email.IEmailGateway;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class EmailSenderJob {

    private final JavaMailSender javaMailSender;
    private final IEmailGateway emailGateway;
    @Value("${spring.mail.host}")
    private String emailFrom;

    @Scheduled(fixedDelay = 10 * 1000)
    public void sendEmails() {
        this.emailGateway.findNotSent().forEach(email -> {
            try {
                final MimeMessage mensagem = javaMailSender.createMimeMessage();
                final MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "utf-8");

                helper.setTo(email.getTarget());
                helper.setFrom(this.emailFrom);
                helper.setSubject(email.getSubject());
                helper.setText(email.getBody(), true);
                javaMailSender.send(mensagem);
                email.send();
                this.emailGateway.update(email);
            } catch (Exception e) {
                this.fail(email, e);
            }
        });
    }

    private void fail(Email email, Exception e) {
        log.warn("Erro ao enviar email para %s do tipo %s".formatted(email.getTarget(), email.getSubject()));
        log.warn("Motivo: %s".formatted(e.getMessage()));
        email.incrementFailedAttempts();
        this.emailGateway.update(email);
    }

}
