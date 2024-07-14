package br.com.ifsp.tickets.infra.config;

import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.EventStatus;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.vo.Address;
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
import java.util.List;
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
    public WebServerConfig(ResourceLoader resourceLoader, IMessageGateway messageGateway, PasswordEncoder passwordEncoder, IUserGateway userGateway, ICompanyGateway companyGateway, IEventGateway eventGateway) {
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
        log.info("Default user created!");

        final Company company = Company.newCompany(
                "IFSP Cubatão",
                "Instituto Federal de Educação, Ciência e Tecnologia de São Paulo",
                new CNPJ("10882594000327"),
                user.getId(),
                Address.with(
                        "Rua Maria Cristina",
                        "",
                        "50",
                        "Jardim Casqueiro",
                        "Cubatão",
                        "São Paulo",
                        "Brasil",
                        "11533160"
                )
        );

        companyGateway.create(company);
        user.joinCompany(company.getId());
        userGateway.update(user);
        log.info("Default company created!");

        Event event = Event.with(
                EventID.unique(),
                "Festa Junina",
                "A Festa Junina é uma tradicional celebração brasileira que ocorre em junho, homenageando os santos populares São João, Santo Antônio e São Pedro. Com danças de quadrilha, fogueiras, comidas típicas como milho e quentão, e trajes caipiras, a festa destaca-se pela sua atmosfera alegre e colorida. É comum ver barracas com brincadeiras como pescaria e correio elegante. A celebração também inclui músicas típicas e uma forte influência das tradições rurais do nordeste do Brasil.",
                LocalDate.of(2024, 6, 29),
                LocalDate.of(2024, 7, 30),
                Address.with(
                        "Rua Maria Cristina",
                        "",
                        "50",
                        "Jardim Casqueiro",
                        "Cubatão",
                        "São Paulo",
                        "Brasil",
                        "11533160"
                ),
                company.getId(),
                EventStatus.OPENED,
                List.of(),
                List.of()
        );

        eventGateway.create(event);
        log.info("Default event created!");

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
