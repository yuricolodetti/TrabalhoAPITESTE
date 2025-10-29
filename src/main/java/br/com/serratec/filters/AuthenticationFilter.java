package br.com.serratec.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.serratec.entity.User;
import br.com.serratec.entity.UserDetailsImpl;
import br.com.serratec.repository.UserRepository;
import br.com.serratec.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Ignora endpoints públicos
        String path = request.getServletPath();
        if (path.equals("/auth/signup") || path.equals("/auth/login") || path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Recupera token do header Authorization
        String token = recoveryToken(request);
        if (token != null) {
            String subject = jwtTokenService.getSubjectFromToken(token);

            // Se o usuário existir, seta autenticação no contexto
            userRepository.findByLogin(subject).ifPresent(user -> {
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUser().getLogin(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        // Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }

    // Método para pegar token do header Authorization
    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
