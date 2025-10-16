package com.upn.ecocollect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad de Spring
public class SecurityConfig {

    // Necesario para que el AuthService pueda autenticar al usuario
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. HABILITAR CORS (utiliza la configuración global de CorsConfig.java)
            .cors(Customizer.withDefaults())
            
            // 2. Deshabilita CSRF
            .csrf(AbstractHttpConfigurer::disable)
            
            // 3. Configuración de Sesiones sin estado para JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 4. Definición de las reglas de autorización
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/**").permitAll() 
                .anyRequest().authenticated()
            );

        // NOTA: El filtro de JWT se añadiría aquí.
        
        return http.build();
    }
}