package com.upn.ecocollect.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "puntos_reciclaje")
public class PuntoReciclaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    private String telefono;

    @Column(nullable = false)
    private String estado; // "activo" | "inactivo" | "mantenimiento"

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "punto_materiales",
        joinColumns = @JoinColumn(name = "punto_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private Set<Material> materialesAceptados = new HashSet<>();
}
