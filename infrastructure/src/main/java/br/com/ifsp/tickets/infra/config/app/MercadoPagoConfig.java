package br.com.ifsp.tickets.infra.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MercadoPagoConfig {

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @PostConstruct
    public void initialize() {
        com.mercadopago.MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
    }
}
