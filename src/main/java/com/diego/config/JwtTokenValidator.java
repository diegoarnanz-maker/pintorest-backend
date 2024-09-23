package com.diego.config;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        
        if (jwt != null ) {
            jwt = jwt.substring(7); // Quita la palabra "Bearer " del token
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
                
                // Parsear el token JWT
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                
                // Extraer el email
                String email = String.valueOf(claims.get("email"));
                
                // Crear objeto Authentication
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
                
                // Establecer el objeto Authentication en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (Exception e) {
                // Manejo de excepción si el token es inválido o ha expirado
                System.out.println("Token inválido o expirado: " + e.getMessage());
            }
        }
        
        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
