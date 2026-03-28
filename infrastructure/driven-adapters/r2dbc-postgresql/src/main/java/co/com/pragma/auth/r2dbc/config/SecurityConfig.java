package co.com.pragma.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        // Permitir acceso sin autenticación a Swagger UI y Actuator
                        .pathMatchers("/swagger-ui.html", "/v3/api-docs/**", "/webjars/**").permitAll()
                        .pathMatchers("/actuator/health", "/actuator/prometheus").permitAll()
                        // Permitir acceso a los endpoints de la API (temporal para pruebas)
                        .pathMatchers("/api/roles", "/api/users").permitAll()
                        // Requerir autenticación para cualquier otra solicitud
                        .anyExchange().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
                .httpBasic(withDefaults()) // Usar httpBasic con configuración predeterminada
                .build();
    }
}