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
    public com.upn.ecocollect.dto.AuthResponse registrarNuevoUsuario(RegistroRequest registroRequest) {
        
        // CORRECCIÓN 1: Usa existsByEmail()
        if (usuarioRepository.existsByEmail(registroRequest.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroRequest.getNombre());
        nuevoUsuario.setEmail(registroRequest.getEmail());
        nuevoUsuario.setTelefono(registroRequest.getTelefono());
        nuevoUsuario.setDireccion(registroRequest.getDireccion());
        
        // Rol: por defecto CLIENTE; si el DTO envía RECOLECTOR o CLIENTE, usarlo.
        RolUsuario rolAsignado = RolUsuario.CLIENTE;
        try {
            String rolDto = registroRequest.getRol();
            if (rolDto != null) {
                RolUsuario rolReq = RolUsuario.valueOf(rolDto.toUpperCase());
                if (rolReq == RolUsuario.RECOLECTOR || rolReq == RolUsuario.CLIENTE) {
                    rolAsignado = rolReq;
                }
            }
        } catch (IllegalArgumentException ignored) {
            // Si envían un valor desconocido, mantener CLIENTE
        }
        nuevoUsuario.setRol(rolAsignado);
        
        nuevoUsuario.setPassword(passwordEncoder.encode(registroRequest.getPassword())); 
        Usuario saved = usuarioRepository.save(nuevoUsuario);

        // Generar token y refresh token para la nueva cuenta (opcional)
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRol().name());
        String refreshToken = java.util.UUID.randomUUID().toString();

        // NOTE: refreshToken persistence is not implemented here. For production store it in DB.

        return new com.upn.ecocollect.dto.AuthResponse(saved, token, refreshToken);
    }
    
    // ----------------------------------------------------------------------
    // 2. LOGIN (Corregido manejo de Optional y conversión de Rol a String)
    // ----------------------------------------------------------------------
    public com.upn.ecocollect.dto.AuthResponse autenticarUsuario(LoginRequest loginRequest) {
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
            
            // 4. Generar el JWT y refresh token
            String token = jwtUtil.generateToken(email, rolString);
            String refreshToken = java.util.UUID.randomUUID().toString();

            // NOTE: refreshToken persistence is not implemented here. For production store it in DB.

            return new com.upn.ecocollect.dto.AuthResponse(usuario, token, refreshToken);

        } catch (AuthenticationException e) {
            // Capturar error de autenticación (credenciales inválidas)
            throw new RuntimeException("Credenciales inválidas. Por favor, verifica tu email y contraseña.");
        }
    }
}