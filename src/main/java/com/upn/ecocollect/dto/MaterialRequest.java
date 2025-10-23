package com.upn.ecocollect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MaterialRequest {

    @NotBlank(message = "El nombre del material es obligatorio")
    private String nombre;

    @NotNull(message = "El precio por kg es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double precioPorKg;
}
