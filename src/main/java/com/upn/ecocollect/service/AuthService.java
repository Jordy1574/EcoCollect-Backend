package com.upn.ecocollect.service;

import com.upn.ecocollect.dto.LoginRequest;
import com.upn.ecocollect.dto.RegistroRequest;
import com.upn.ecocollect.model.RolUsuario;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.repository.UsuarioRepository;
import com.upn.ecocollect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional; // ¡Importante!

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; 

    @Autowired
    private JwtUtil jwtUtil; 

    // ----------------------------------------------------------------------
    // 1. REGISTRO (Corregido existsByEmail y asignación de rol)
    // ----------------------------------------------------------------------
    public Usuario registrarNuevoUsuario(RegistroRequest registroRequest) {
        
        // CORRECCIÓN 1: Usa existsByEmail()
        if (usuarioRepository.existsByEmail(registroRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroRequest.getNombre());
        nuevoUsuario.setEmail(registroRequest.getEmail());
        nuevoUsuario.setTelefono(registroRequest.getTelefono());
        nuevoUsuario.setDireccion(registroRequest.getDireccion());
        
        // CORRECCIÓN 2: Asigna el rol por defecto (CLIENTE), no usa getRol() del DTO
        nuevoUsuario.setRol(RolUsuario.CLIENTE); // ¡Asegúrate que el rol sea un String en el modelo!
        
        nuevoUsuario.setPassword(passwordEncoder.encode(registroRequest.getPassword())); 
        
        return usuarioRepository.save(nuevoUsuario);
    }
    
    // ----------------------------------------------------------------------
    // 2. LOGIN (Corregido manejo de Optional y conversión de Rol a String)
    // ----------------------------------------------------------------------
    public String autenticarUsuario(LoginRequest loginRequest) {
        try {
            // 1. Intenta autenticar
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            // 2. Recuperar email del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            
            // 3. Buscar el objeto Usuario completo para obtener el rol
            // CORRECCIÓN 3: Uso de Optional para evitar Type Mismatch
            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
            
            if (optionalUsuario.isEmpty()) {
                 throw new RuntimeException("Error interno: Usuario no encontrado después de la autenticación.");
            }
            Usuario usuario = optionalUsuario.get();
            
            String rolString = usuario.getRol().name(); // Asumiendo que getRol() devuelve String
            
            // 4. Generar el JWT
            return jwtUtil.generateToken(email, rolString);

        } catch (AuthenticationException e) {
            // Capturar error de autenticación (credenciales inválidas)
            throw new RuntimeException("Credenciales inválidas. Por favor, verifica tu email y contraseña.");
        }
    }
}