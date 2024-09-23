package com.diego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        http.sessionManagement(management -> management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)).authorizeHttpRequests(
                Authorize -> Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(withDefaults());
                    
        
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

    return request -> {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // Permitir solo el frontend
        cfg.setAllowedMethods(Collections.singletonList("*")); // Permitir todos los métodos (puedes especificar los necesarios)
        cfg.setAllowedHeaders(Collections.singletonList("*")); // Permitir todos los headers
        cfg.setAllowCredentials(true); // Habilitar credenciales (cookies, autorizaciones)
        return cfg;
    };
}


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
