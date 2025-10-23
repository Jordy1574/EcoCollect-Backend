package com.upn.ecocollect.repository;

import com.upn.ecocollect.model.PuntoReciclaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntoReciclajeRepository extends JpaRepository<PuntoReciclaje, Long> {
    List<PuntoReciclaje> findByEstado(String estado);
    List<PuntoReciclaje> findByNombreContainingIgnoreCase(String nombre);
}
