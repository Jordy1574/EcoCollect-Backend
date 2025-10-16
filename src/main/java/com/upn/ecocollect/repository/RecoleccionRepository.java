package com.upn.ecocollect.repository;

import java.util.List;
import com.upn.ecocollect.model.EstadoRecoleccion;
import com.upn.ecocollect.model.Recoleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoleccionRepository extends JpaRepository<Recoleccion, Long> {
    // Buscar todas las solicitudes que estén PENDIENTES, vital para el Admin/Recolector.
    // Spring crea el código SQL por el nombre del método
    List<Recoleccion> findByEstado(EstadoRecoleccion estado);
}