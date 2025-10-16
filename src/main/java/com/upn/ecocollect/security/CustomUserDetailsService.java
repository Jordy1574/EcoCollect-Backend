package com.upn.ecocollect.security;

import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    // El "username" para Spring Security es el email que usas para el Login
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        // 1. Buscar el usuario en la base de datos por email
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        // 2. Si no lo encuentra, lanza una excepción (Login falla)
        if (optionalUsuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        Usuario usuario = optionalUsuario.get();

        // 3. Devolver un objeto UserDetails de Spring Security
        // Spring lo usa para: 
        // a) Comparar la contraseña encriptada
        // b) Obtener los roles
        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getPassword(), // Contraseña encriptada de la BD
            
            // Asignar los roles (Authorities)
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
        );
    }
}