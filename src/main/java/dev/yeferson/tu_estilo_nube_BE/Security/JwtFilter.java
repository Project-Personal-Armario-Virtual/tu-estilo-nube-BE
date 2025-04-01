package dev.yeferson.tu_estilo_nube_BE.Security;

import dev.yeferson.tu_estilo_nube_BE.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    throws ServletException, IOException {
String authorizationHeader = request.getHeader("Authorization");
String token = null;
String username = null;

if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    token = authorizationHeader.substring(7); // Quita "Bearer " del header
    username = jwtUtil.getUsernameFromToken(token);
}

if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    UserDetails userDetails = userService.loadUserByUsername(username);
    
    // Cambio: Añadir userDetails como parámetro para validar el token
    if (jwtUtil.validateToken(token)) {
        UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}

filterChain.doFilter(request, response); // Continúa con la cadena de filtros
}
    
    
}
