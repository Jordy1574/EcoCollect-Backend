package com.upn.ecocollect.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
    // El rol será por defecto CLIENTE al registrarse, el ADMIN cambia esto.
    
    private String telefono;
    private String direccion;

    // Opcional: permitir que el frontend envíe "rol" para elegir entre CLIENTE o RECOLECTOR.
    // ADMIN no será aceptado por seguridad en el endpoint de registro.
    private String rol; // valores esperados: "CLIENTE" | "RECOLECTOR" (case-insensitive)
}