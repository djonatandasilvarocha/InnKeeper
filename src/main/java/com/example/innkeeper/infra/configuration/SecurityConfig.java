
package com.example.innkeeper.infra.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println("ERRO DE AUTENTICAÇÃO: " + authException.getMessage());

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Token invalido");
                        })

                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            System.out.println("ACESSO NEGADO: " + accessDeniedException.getMessage());

                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Acesso negado");
                        })
                )

                // ==========================================
                // TESTE DA PROVA - PARTE 1
                // Substituir temporariamente este bloco
                // por uma regra que libera todas as rotas.
                // ==========================================

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/auth/login",
                                "/usuarios/login",
                                "/usuarios/admin",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/usuarios",
                                "/usuarios/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/quarto/**")
                        .hasAnyRole("ADMIN", "FUNCIONARIO")

                        .requestMatchers(HttpMethod.DELETE, "/quarto/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/hospede/**")
                        .hasAnyRole("ADMIN", "FUNCIONARIO", "HOSPEDE")

                        .anyRequest().authenticated()
                )

                // ==========================================
                // TESTE DA PROVA - PARTE 2
                // Comentar temporariamente o filtro JWT.
                // ==========================================

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
