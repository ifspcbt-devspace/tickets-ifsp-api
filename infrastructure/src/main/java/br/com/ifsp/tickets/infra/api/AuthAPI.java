package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterRequest;
import br.com.ifsp.tickets.infra.user.models.register.RegisterResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
public interface AuthAPI {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request);
}
