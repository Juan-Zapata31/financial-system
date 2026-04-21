package app.infrastructure.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()

                    // InternalAnalyst
                    .requestMatchers("/analyst/**").hasRole("InternalAnalyst")
                    .requestMatchers("/admin/users/**").hasRole("InternalAnalyst")

                    // Teller
                    .requestMatchers("/teller/**").hasRole("Teller")

                    // CommercialEmployee
                    .requestMatchers("/commercial/**")
                            .hasAnyRole("CommercialEmployee", "InternalAnalyst")

                    // Loans
                    .requestMatchers(HttpMethod.POST, "/loans")
                            .hasAnyRole("CommercialEmployee", "IndividualClient", "CompanyClient")
                    .requestMatchers(HttpMethod.POST, "/loans/*/approve").hasRole("InternalAnalyst")
                    .requestMatchers(HttpMethod.POST, "/loans/*/reject").hasRole("InternalAnalyst")
                    .requestMatchers(HttpMethod.POST, "/loans/*/disburse").hasRole("InternalAnalyst")
                    .requestMatchers(HttpMethod.GET, "/loans/**")
                            .hasAnyRole("InternalAnalyst", "CommercialEmployee")

                    // IndividualClient
                    .requestMatchers("/client/individual/**").hasRole("IndividualClient")

                    // CompanyClient
                    .requestMatchers("/client/company/**").hasRole("CompanyClient")

                    // CompanyEmployee
                    .requestMatchers(HttpMethod.POST, "/company/transactions/**")
                            .hasRole("CompanyEmployee")
                    .requestMatchers(HttpMethod.GET, "/company/**")
                            .hasAnyRole("CompanyEmployee", "CompanySupervisor")

                    // CompanySupervisor
                    .requestMatchers("/company/approvals/**").hasRole("CompanySupervisor")
                    .requestMatchers(HttpMethod.POST, "/company/loans")
                            .hasAnyRole("CompanySupervisor", "CompanyEmployee")

                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(
                            "{\"status\":401,\"message\":\"No autenticado: se requiere un token valido\",\"errors\":null}");
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(
                            "{\"status\":403,\"message\":\"Acceso denegado: no tiene permisos\",\"errors\":null}");
                    })
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
