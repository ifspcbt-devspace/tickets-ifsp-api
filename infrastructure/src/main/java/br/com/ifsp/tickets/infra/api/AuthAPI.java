package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.user.models.login.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.login.LoginResponse;
import br.com.ifsp.tickets.infra.user.models.register.RegisterRequest;
import br.com.ifsp.tickets.infra.user.models.register.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
public interface AuthAPI {

    @RequestMapping(value = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @RequestMapping(value = "/register", consumes = "application/json", produces = "application/json")
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request);
}
