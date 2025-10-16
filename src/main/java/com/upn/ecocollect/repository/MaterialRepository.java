package com.upn.ecocollect.repository;

import com.upn.ecocollect.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    // Aquí puede añadir métodos de búsqueda si los necesita, como buscar por nombre
}