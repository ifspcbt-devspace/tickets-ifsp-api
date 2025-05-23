package br.com.ifsp.tickets.infra.config.security;

import br.com.ifsp.tickets.app.administrative.auth.IAuthUtils;
import br.com.ifsp.tickets.infra.config.security.entrypoint.AuthEntryPointJwt;
import br.com.ifsp.tickets.infra.config.security.filter.BasicAuthFilter;
import br.com.ifsp.tickets.infra.config.security.filter.JwtAuthFilter;
import br.com.ifsp.tickets.infra.config.security.service.CustomUserDetailsService;
import br.com.ifsp.tickets.infra.contexts.administrative.user.AuthUtils;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
//          Swagger docs
            "/docs/api-docs",
            "/docs/**",
            "/docs/swagger.html",
            "/error",
            "/error/**",
//          Api endpoints
            "/v1/auth/login",
            "/v1/auth/register",
            "/v1/auth/activate/*",
            "/v1/auth/recovery/**",
            "/v1/cep/**",
            "/v1/billing/listener"
    };

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final UserRepository userRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder pe) {
        final DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(
                username -> this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)))
        );
        auth.setPasswordEncoder(pe);
        return auth;
    }

    @Bean
    public IAuthUtils authUtils(@Value("${security.jwt.secret-key}") String secretKey) {
        return new AuthUtils(passwordEncoder(), secretKey);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, IAuthUtils authService, AuthenticationProvider authenticationProvider) throws Exception {
        final CorsConfiguration cors = new CorsConfiguration().applyPermitDefaultValues();
        cors.addAllowedMethod(HttpMethod.PUT);
        cors.addAllowedMethod(HttpMethod.PATCH);
        cors.addAllowedMethod(HttpMethod.GET);
        cors.addAllowedMethod(HttpMethod.DELETE);
        return httpSecurity
                .addFilterAfter(new BasicAuthFilter(customUserDetailsService), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthFilter(authService, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .cors(crs -> crs.configurationSource(request -> cors))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/v1/event/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/event/*/thumbnail").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/product/*/ticketSale").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/event/search").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(config -> config.authenticationEntryPoint(this.authEntryPointJwt))
                .build();
    }

}
