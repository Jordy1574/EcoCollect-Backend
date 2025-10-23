package com.upn.ecocollect.service;

import com.upn.ecocollect.dto.UsuarioAdminRequest;
import com.upn.ecocollect.model.RolUsuario;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioAdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario createUsuario(UsuarioAdminRequest request) {
        // Verificar que no exista email
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        
        // Hashear contraseña
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria al crear un usuario");
        }
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Asignar rol
        try {
            usuario.setRol(RolUsuario.valueOf(request.getRol().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + request.getRol());
        }
        
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, UsuarioAdminRequest request) {
        Usuario usuario = getUsuarioById(id);
        
        // Verificar email único si cambió
        if (!usuario.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Ya existe un usuario con ese email");
            }
        }
        
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        
        // Actualizar contraseña solo si se envió una nueva
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        // Actualizar rol
        try {
            usuario.setRol(RolUsuario.valueOf(request.getRol().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido: " + request.getRol());
        }
        
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }
}
