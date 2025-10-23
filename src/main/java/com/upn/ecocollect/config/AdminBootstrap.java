package com.upn.ecocollect.config;

import com.upn.ecocollect.model.RolUsuario;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    @Value("${ecocollect.admin.email:admin@ecocollect.com}")
    private String defaultAdminEmail;

    @Value("${ecocollect.admin.password:Admin123!}")
    private String defaultAdminPassword;

    @Bean
    public CommandLineRunner createDefaultAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.findByEmail(defaultAdminEmail).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setEmail(defaultAdminEmail);
                admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
                admin.setRol(RolUsuario.ADMIN);
                admin.setTelefono(null);
                admin.setDireccion(null);
                usuarioRepository.save(admin);
                log.info("Admin por defecto creado: {} (puedes cambiar email y password por propiedades)", defaultAdminEmail);
            } else {
                log.info("Admin por defecto ya existe: {}", defaultAdminEmail);
            }
        };
    }
}
