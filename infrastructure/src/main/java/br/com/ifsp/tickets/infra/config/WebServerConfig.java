package br.com.ifsp.tickets.infra.config;

import br.com.ifsp.tickets.domain.communication.message.IMessageGateway;
import br.com.ifsp.tickets.domain.communication.message.Message;
import br.com.ifsp.tickets.domain.communication.message.type.MessageSubject;
import br.com.ifsp.tickets.domain.communication.message.type.MessageType;
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

import java.io.IOException;
import java.io.InputStream;
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
    public WebServerConfig(ResourceLoader resourceLoader, IMessageGateway messageGateway) {
        this.resourceLoader = resourceLoader;

        log.info("Creating default messages...");
        try {

            String content = this.loadFileContent("/communication/password-recovery.html");
            messageGateway.update(Message.create(MessageSubject.PASSWORD_RECOVERY, content, MessageType.HTML));

            content = this.loadFileContent("/communication/account-activation.html");
            messageGateway.update(Message.create(MessageSubject.ACCOUNT_ACTIVATION, content, MessageType.HTML));

            log.info("Default messages created!");
        } catch (IOException e) {
            log.error("Error loading file content", e);
        }
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
