package com.upn.ecocollect.repository;

import com.upn.ecocollect.model.Usuario;
//import com.upn.ecocollect.model.RolUsuario; // Asegúrate de tener este import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Asegúrate de tener este import si no está

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método anterior (devuelve Optional para manejo más limpio)
    Optional<Usuario> findByEmail(String email); 

    // ¡NUEVO MÉTODO para verificar si existe!
    boolean existsByEmail(String email); 
}