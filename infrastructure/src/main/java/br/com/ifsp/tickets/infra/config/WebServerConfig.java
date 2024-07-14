package br.com.ifsp.tickets.infra.config;

import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;

import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.Role;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan("br.com.ifsp.tickets.infra")
@PropertySource("classpath:.env")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Tickets API", version = "v1", description = "Tickets API Documentation", contact = @Contact(name = "Leonardo da Silva", email = "oproprioleonardo@gmail.com", url = "https://linktr.ee/_oleonardosilva")))
public class WebServerConfig {

    private final ResourceLoader resourceLoader;

    @Autowired
    public WebServerConfig(ResourceLoader resourceLoader, IMessageGateway messageGateway, PasswordEncoder passwordEncoder, IUserGateway userGateway) {
        this.resourceLoader = resourceLoader;

        log.info("Creating default messages...");
        try {

            String content = this.loadFileContent("/communication/password-recovery.html");
            messageGateway.update(Message.create(MessageSubject.PASSWORD_RECOVERY, content, MessageType.HTML));

            content = this.loadFileContent("/communication/account-activation.html");
            messageGateway.update(Message.create(MessageSubject.ACCOUNT_ACTIVATION, content, MessageType.HTML));

            content = this.loadFileContent("/communication/event-ticket.html");
            messageGateway.update(Message.create(MessageSubject.EVENT_TICKET, content, MessageType.HTML));

            log.info("Default messages created!");
        } catch (IOException e) {
            log.error("Error loading file content", e);
        }


        log.info("Creating default users...");

        User user = User.with(
                UserID.unique(),
                "Leonardo",
                Role.COMPANY_MANAGER,
                "admin",
                new EmailAddress("l.6042silva@gmail.com"),
                new PhoneNumber("11999999999"),
                "c_manager",
                passwordEncoder.encode("c_manager"),
                new CPF("63640027060"),
                LocalDate.of(1999, 4, 2),
                null,
                true,
                null
        );

        userGateway.create(user);

        user = User.with(
                UserID.unique(),
                "Mateus",
                Role.CUSTOMER,
                "admin",
                new EmailAddress("oproprioleonardo@gmail.com"),
                new PhoneNumber("(16) 3496-1354"),
                "cliente",
                passwordEncoder.encode("cliente"),
                new CPF("15303139026"),
                LocalDate.of(1999, 4, 2),
                null,
                true,
                null
        );

        userGateway.create(user);

        log.info("Default users created!");
    }

    private String loadFileContent(String filePath) throws IOException {
        final Resource resource = resourceLoader.getResource("classpath:" + filePath);
        try (InputStream inputStream = resource.getInputStream(); Scanner scanner = new Scanner(inputStream)) {
            final StringBuilder contentBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine()).append("\n");
            }
            return contentBuilder.toString();
        }
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

}
