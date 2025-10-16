package com.upn.ecocollect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean // Esta anotación hace que Spring gestione este objeto
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}