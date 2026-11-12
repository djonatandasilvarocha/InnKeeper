
package com.example.innkeeper.infra.configuration;

import com.example.innkeeper.application.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String uri = request.getRequestURI();

        System.out.println("URI RECEBIDA: " + uri);

        // Rotas públicas
        if (uri.startsWith("/swagger-ui")
                || uri.startsWith("/v2/api-docs")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/swagger-resources")
                || uri.startsWith("/webjars")
                || uri.startsWith("/auth/login")
                || uri.startsWith("/usuarios/login")
                || uri.startsWith("/usuarios/admin")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                var usuarioLogado = tokenService.verificarToken(token);

                var authentication = new UsernamePasswordAuthenticationToken(
                        usuarioLogado,
                        null,
                        usuarioLogado.authorities()
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                System.out.println("TOKEN VALIDADO COM SUCESSO!");
                System.out.println("USUARIO: " + usuarioLogado.email());
                System.out.println("AUTHORITIES: " + usuarioLogado.authorities());
                System.out.println("AUTHENTICATED: " +
                        SecurityContextHolder.getContext()
                                .getAuthentication().isAuthenticated());

            } catch (Exception e) {

                SecurityContextHolder.clearContext();

                System.out.println("ERRO AO VALIDAR TOKEN:");
                System.out.println("Mensagem: " + e.getMessage());

                e.printStackTrace();
            }

        } else {
            System.out.println("NENHUM TOKEN BEARER RECEBIDO!");
        }

        filterChain.doFilter(request, response);
    }
}
