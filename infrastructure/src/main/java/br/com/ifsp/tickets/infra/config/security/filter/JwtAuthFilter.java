package br.com.ifsp.tickets.infra.config.security.filter;

import br.com.ifsp.tickets.app.auth.IAuthUtils;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.infra.config.security.service.CustomUserDetailsService;
import br.com.ifsp.tickets.infra.contexts.user.persistence.UserJpaEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final IAuthUtils authService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @Nullable HttpServletRequest request,
            @Nullable HttpServletResponse response,
            @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        assert request != null;
        final String authHeader = request.getHeader("Authorization");
        if ((authHeader == null || (!authHeader.startsWith("Bearer ")) && !authHeader.startsWith("bearer ")) && filterChain != null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            assert authHeader != null;
            final String jwt = authHeader.substring(7);
            final UUID uuid = this.authService.getUuidFromToken(jwt);

            if (uuid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final User userDetails = this.customUserDetailsService.getByUUID(uuid);
                final UserJpaEntity userJpa = UserJpaEntity.from(userDetails);
                if (this.authService.isTokenValid(jwt, userDetails)) {
                    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userJpa,
                            null,
                            userJpa.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        assert filterChain != null;
        filterChain.doFilter(request, response);
    }
}
