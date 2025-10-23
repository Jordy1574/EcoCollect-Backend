package com.upn.ecocollect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upn.ecocollect.model.RolUsuario;
import com.upn.ecocollect.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private RolUsuario rol;
    private String telefono;
    private String direccion;
    
    // ✅ CAMPO ALIAS: Frontend espera "distrito" pero la entidad tiene "direccion"
    @JsonProperty("distrito")
    public String getDistrito() {
        return this.direccion;
    }
    
    // ✅ CAMPO PLACEHOLDER: Frontend espera un campo "estado"
    public String getEstado() {
        return "Activo";
    }
    
    // Constructor desde entidad
    public static UsuarioResponse fromEntity(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setTelefono(usuario.getTelefono());
        response.setDireccion(usuario.getDireccion());
        return response;
    }
}
