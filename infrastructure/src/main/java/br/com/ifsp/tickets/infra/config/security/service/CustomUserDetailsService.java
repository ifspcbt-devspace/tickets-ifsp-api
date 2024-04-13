package br.com.ifsp.tickets.infra.config.security.service;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.infra.user.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public User getByUUID(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException("Token is not valid")).toAggregate();
    }
}
