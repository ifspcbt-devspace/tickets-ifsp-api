package br.com.ifsp.tickets.infra.config.security.filter;

import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.infra.config.security.service.CustomUserDetailsService;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class BasicAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;


    public BasicAuthFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain chain) throws IOException, ServletException {
        assert request != null;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            try {
                final String base64Credentials = authHeader.substring("Basic ".length()).trim();
                final String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                // credentials = username:password
                final String[] values = credentials.split(":", 2);

                final User userDetails = this.userDetailsService.getByEmail(values[0]);
                final UserJpaEntity userJpa = UserJpaEntity.from(userDetails);
                if (!BCrypt.checkpw(values[1], userJpa.getPassword())) {
                    assert chain != null;
                    chain.doFilter(request, response);
                    return;
                }

                final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userJpa,
                        null,
                        userJpa.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (final Exception e) {
                log.info(e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        assert chain != null;
        chain.doFilter(request, response);
    }

}
