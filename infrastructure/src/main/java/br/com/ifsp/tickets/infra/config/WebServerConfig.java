package br.com.ifsp.tickets.infra.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan("br.com.ifsp.tickets.infra")
@PropertySource("classpath:.env")
@Slf4j
public class WebServerConfig {

    public WebServerConfig() {
        log.info("WebServerConfig initialized");
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
        log.info("Secret key: {}", keyString);
    }

}
