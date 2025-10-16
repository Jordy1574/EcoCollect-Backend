package com.upn.ecocollect.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recolecciones")
public class Recoleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el cliente que solicitó el servicio (Foreign Key a la tabla 'usuarios')
    @ManyToOne 
    @JoinColumn(name = "cliente_id", nullable = false) 
    private Usuario cliente; 
    
    private String direccionRecojo; // Dirección donde se realizará el servicio
    
    @Enumerated(EnumType.STRING)
    private EstadoRecoleccion estado = EstadoRecoleccion.PENDIENTE; // Estado de la solicitud

    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    private LocalDateTime fechaAsignacion; // Cuando el admin asigna la ruta
    private LocalDateTime fechaCompletada; // Cuando el recolector finaliza

    // Relación para saber qué recolector se hará cargo
    @ManyToOne 
    @JoinColumn(name = "recolector_id") // Puede ser NULL si aún no ha sido asignada
    private Usuario recolector; 
}