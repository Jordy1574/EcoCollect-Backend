package com.upn.ecocollect.model;

import jakarta.persistence.*;
import lombok.Data; // De la dependencia Lombok

@Data // Proporciona Getters, Setters, etc.
@Entity // Indica que esta clase es una tabla de BD
@Table(name = "usuarios")

public class Usuario {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(unique = true, nullable = false) // Único y obligatorio para el login
    private String email; 
    
    @Column(nullable = false)
    private String password; 
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Guarda el rol como texto (ADMIN, CLIENTE, RECOLECTOR)
    private RolUsuario rol; 

    private String telefono;
    private String direccion; // Dirección principal del cliente o base del recolector
}