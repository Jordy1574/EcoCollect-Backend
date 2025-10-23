package com.upn.ecocollect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioAdminRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password; // Opcional para actualización

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // "ADMIN" | "RECOLECTOR" | "CLIENTE"

    private String telefono;
    private String direccion;
}
