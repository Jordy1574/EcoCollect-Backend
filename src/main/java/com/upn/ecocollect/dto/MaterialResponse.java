package com.upn.ecocollect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upn.ecocollect.model.Material;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MaterialResponse {
    private Long id;
    private String nombre;
    private Double precioPorKg;
    
    // ✅ CAMPO CALCULADO: Infiere el tipo desde el nombre del material
    public String getTipo() {
        if (this.nombre == null) return "otros";
        
        String nombreLower = this.nombre.toLowerCase();
        if (nombreLower.contains("plast") || nombreLower.contains("pet") || nombreLower.contains("hdpe")) {
            return "plastico";
        }
        if (nombreLower.contains("papel") || nombreLower.contains("carton")) {
            return "papel";
        }
        if (nombreLower.contains("vidri")) {
            return "vidrio";
        }
        if (nombreLower.contains("metal") || nombreLower.contains("aluminio") || nombreLower.contains("cobre")) {
            return "metal";
        }
        if (nombreLower.contains("electron")) {
            return "electronico";
        }
        return "otros";
    }
    
    // ✅ CAMPO CALCULADO: Info adicional para el frontend
    @JsonProperty("info")
    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("precioPromedio", String.format("S/. %.2f/kg", this.precioPorKg));
        info.put("puntosActivos", "0");
        info.put("ultimaActualizacion", "Hoy");
        return info;
    }
    
    // Constructor desde entidad
    public static MaterialResponse fromEntity(Material material) {
        MaterialResponse response = new MaterialResponse();
        response.setId(material.getId());
        response.setNombre(material.getNombre());
        response.setPrecioPorKg(material.getPrecioPorKg());
        return response;
    }
}
