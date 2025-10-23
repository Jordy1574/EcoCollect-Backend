package com.upn.ecocollect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class PuntoReciclajeRequest {

    @NotBlank(message = "El nombre del punto de reciclaje es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String telefono;

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // "activo" | "inactivo" | "mantenimiento"

    @NotNull(message = "Los materiales aceptados son obligatorios")
    private Set<Long> materialesAceptadosIds; // IDs de los materiales
}
