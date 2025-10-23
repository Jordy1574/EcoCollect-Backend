package com.upn.ecocollect.service;

import com.upn.ecocollect.dto.PuntoReciclajeRequest;
import com.upn.ecocollect.model.Material;
import com.upn.ecocollect.model.PuntoReciclaje;
import com.upn.ecocollect.repository.MaterialRepository;
import com.upn.ecocollect.repository.PuntoReciclajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PuntoReciclajeService {

    @Autowired
    private PuntoReciclajeRepository puntoReciclajeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<PuntoReciclaje> getAllPuntos() {
        return puntoReciclajeRepository.findAll();
    }

    public PuntoReciclaje getPuntoById(Long id) {
        return puntoReciclajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Punto de reciclaje no encontrado con id: " + id));
    }

    public PuntoReciclaje createPunto(PuntoReciclajeRequest request) {
        PuntoReciclaje punto = new PuntoReciclaje();
        punto.setNombre(request.getNombre());
        punto.setDireccion(request.getDireccion());
        punto.setTelefono(request.getTelefono());
        punto.setEstado(request.getEstado());
        
        // Cargar los materiales por sus IDs
        Set<Material> materiales = new HashSet<>();
        for (Long materialId : request.getMaterialesAceptadosIds()) {
            Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con id: " + materialId));
            materiales.add(material);
        }
        punto.setMaterialesAceptados(materiales);
        
        return puntoReciclajeRepository.save(punto);
    }

    public PuntoReciclaje updatePunto(Long id, PuntoReciclajeRequest request) {
        PuntoReciclaje punto = getPuntoById(id);
        
        punto.setNombre(request.getNombre());
        punto.setDireccion(request.getDireccion());
        punto.setTelefono(request.getTelefono());
        punto.setEstado(request.getEstado());
        
        // Actualizar materiales
        Set<Material> materiales = new HashSet<>();
        for (Long materialId : request.getMaterialesAceptadosIds()) {
            Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con id: " + materialId));
            materiales.add(material);
        }
        punto.setMaterialesAceptados(materiales);
        
        return puntoReciclajeRepository.save(punto);
    }

    public void deletePunto(Long id) {
        PuntoReciclaje punto = getPuntoById(id);
        puntoReciclajeRepository.delete(punto);
    }

    public List<PuntoReciclaje> searchPuntos(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllPuntos();
        }
        return puntoReciclajeRepository.findByNombreContainingIgnoreCase(query);
    }

    public List<PuntoReciclaje> getPuntosByEstado(String estado) {
        return puntoReciclajeRepository.findByEstado(estado);
    }
}
