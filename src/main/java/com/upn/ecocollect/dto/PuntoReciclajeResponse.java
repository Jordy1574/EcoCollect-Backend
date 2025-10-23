package com.upn.ecocollect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upn.ecocollect.model.PuntoReciclaje;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PuntoReciclajeResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String estado;
    private List<MaterialResponse> materialesAceptados;
    
    // ✅ CAMPO PLACEHOLDER: Frontend espera un campo "tipo"
    public String getTipo() {
        return "principal";
    }
    
    // ✅ CAMPO PLACEHOLDER: Frontend espera un campo "tipoTexto"
    @JsonProperty("tipoTexto")
    public String getTipoTexto() {
        return "Centro Principal";
    }
    
    // ✅ CAMPO PLACEHOLDER: Frontend espera un campo "horario"
    public String getHorario() {
        return "";
    }
    
    // Constructor desde entidad
    public static PuntoReciclajeResponse fromEntity(PuntoReciclaje punto) {
        PuntoReciclajeResponse response = new PuntoReciclajeResponse();
        response.setId(punto.getId());
        response.setNombre(punto.getNombre());
        response.setDireccion(punto.getDireccion());
        response.setTelefono(punto.getTelefono());
        response.setEstado(punto.getEstado());
        
        // Convertir materiales a DTOs
        if (punto.getMaterialesAceptados() != null) {
            response.setMaterialesAceptados(
                punto.getMaterialesAceptados().stream()
                    .map(MaterialResponse::fromEntity)
                    .collect(Collectors.toList())
            );
        }
        
        return response;
    }
}
