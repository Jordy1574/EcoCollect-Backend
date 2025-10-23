package com.upn.ecocollect.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre; // Ej: Plástico PET, Cartón, Metal, Vidrio
    
    @Column(nullable = false)
    private Double precioPorKg; // Precio actual fijado por el Admin
    
    
}