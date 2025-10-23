package com.upn.ecocollect.service;

import com.upn.ecocollect.dto.MaterialRequest;
import com.upn.ecocollect.model.Material;
import com.upn.ecocollect.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> getAllMateriales() {
        return materialRepository.findAll();
    }

    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material no encontrado con id: " + id));
    }

    public Material createMaterial(MaterialRequest request) {
        // Verificar que no exista material con ese nombre
        if (materialRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un material con ese nombre");
        }

        Material material = new Material();
        material.setNombre(request.getNombre());
        material.setPrecioPorKg(request.getPrecioPorKg());
        
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long id, MaterialRequest request) {
        Material material = getMaterialById(id);
        
        // Verificar nombre único si cambió
        if (!material.getNombre().equals(request.getNombre())) {
            if (materialRepository.findByNombre(request.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe un material con ese nombre");
            }
        }
        
        material.setNombre(request.getNombre());
        material.setPrecioPorKg(request.getPrecioPorKg());
        
        return materialRepository.save(material);
    }

    public void deleteMaterial(Long id) {
        Material material = getMaterialById(id);
        materialRepository.delete(material);
    }

    public List<Material> searchMateriales(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllMateriales();
        }
        return materialRepository.findByNombreContainingIgnoreCase(query);
    }
}
