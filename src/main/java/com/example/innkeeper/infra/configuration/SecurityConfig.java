
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

                        // Quando o usuário não está autenticado
                        .authenticationEntryPoint((request, response, authException) -> {

                            System.out.println("ERRO DE AUTENTICAÇÃO: " + authException.getMessage());

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Token invalido");
                        })

                        // Quando o usuário está autenticado, mas não tem permissão
                        .accessDeniedHandler((request, response, accessDeniedException) -> {

                            System.out.println("ACESSO NEGADO: " + accessDeniedException.getMessage());

                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("text/plain;charset=UTF-8");
                            response.getWriter().println("Acesso negado");
                        })
                )

                .authorizeHttpRequests(auth -> auth

                        // Requisições OPTIONS liberadas
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rotas públicas
                        .requestMatchers(
                                "/auth/login",
                                "/usuarios/login",
                                "/usuarios/admin",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Cadastro público de usuários
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        // Somente ADMIN pode consultar usuários
                        .requestMatchers(
                                HttpMethod.GET,
                                "/usuarios",
                                "/usuarios/**"
                        ).hasRole("ADMIN")

                        // Permissões dos quartos
                        .requestMatchers(HttpMethod.POST, "/quarto/**")
                        .hasAnyRole("ADMIN", "FUNCIONARIO")

                        .requestMatchers(HttpMethod.DELETE, "/quarto/**")
                        .hasRole("ADMIN")

                        // Permissões dos hóspedes
                        .requestMatchers(HttpMethod.GET, "/hospede/**")
                        .hasAnyRole("ADMIN", "FUNCIONARIO", "HOSPEDE")

                        // Demais rotas exigem autenticação
                        .anyRequest().authenticated()
                )

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
