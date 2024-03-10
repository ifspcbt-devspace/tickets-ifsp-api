package br.com.ifsp.tickets.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServer;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(WebServer.class, args);
    }
}
