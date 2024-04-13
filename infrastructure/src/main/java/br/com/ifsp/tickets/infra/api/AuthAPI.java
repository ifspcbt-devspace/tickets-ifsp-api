package br.com.ifsp.tickets.infra.api;

import br.com.ifsp.tickets.infra.user.models.LoginRequest;
import br.com.ifsp.tickets.infra.user.models.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
public interface AuthAPI {

    @RequestMapping("/login")
    ResponseEntity<LoginResponse> login(LoginRequest request);
}
