package com.upn.ecocollect.repository;

import com.upn.ecocollect.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByNombre(String nombre);
    List<Material> findByNombreContainingIgnoreCase(String nombre);
}