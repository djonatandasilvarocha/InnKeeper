
package com.example.innkeeper.configuration;

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

        // Exibe a rota recebida no console do IntelliJ
        System.out.println("URI RECEBIDA: " + uri);

        // Rotas públicas: não precisam de token JWT
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

        // Recupera o cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");

        // Verifica se existe um token Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                // Valida o token e recupera o usuário
                var usuarioLogado = tokenService.verificarToken(token);

                var authentication = new UsernamePasswordAuthenticationToken(
                        usuarioLogado,
                        null,
                        usuarioLogado.authorities()
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            } catch (Exception e) {

                // Token inválido ou expirado
                SecurityContextHolder.clearContext();

                System.out.println("ERRO AO VALIDAR TOKEN: " + e.getMessage());
            }
        }

        // Continua a execução para o SecurityConfig decidir o acesso
        filterChain.doFilter(request, response);
    }
}
