package com.upn.ecocollect.controller;

import com.upn.ecocollect.dto.ApiResponse;
import com.upn.ecocollect.dto.PuntoReciclajeRequest;
import com.upn.ecocollect.dto.PuntoReciclajeResponse;
import com.upn.ecocollect.model.PuntoReciclaje;
import com.upn.ecocollect.service.PuntoReciclajeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/puntos")
public class PuntoReciclajeController {

    @Autowired
    private PuntoReciclajeService puntoReciclajeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PuntoReciclajeResponse>>> getAllPuntos() {
        try {
            List<PuntoReciclaje> puntos = puntoReciclajeService.getAllPuntos();
            List<PuntoReciclajeResponse> responses = puntos.stream()
                .map(PuntoReciclajeResponse::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al obtener puntos de reciclaje: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PuntoReciclajeResponse>> getPuntoById(@PathVariable Long id) {
        try {
            PuntoReciclaje punto = puntoReciclajeService.getPuntoById(id);
            return ResponseEntity.ok(ApiResponse.success(PuntoReciclajeResponse.fromEntity(punto)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PuntoReciclajeResponse>> createPunto(@Valid @RequestBody PuntoReciclajeRequest request) {
        try {
            PuntoReciclaje punto = puntoReciclajeService.createPunto(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(PuntoReciclajeResponse.fromEntity(punto), "Punto de reciclaje creado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PuntoReciclajeResponse>> updatePunto(
            @PathVariable Long id,
            @Valid @RequestBody PuntoReciclajeRequest request) {
        try {
            PuntoReciclaje punto = puntoReciclajeService.updatePunto(id, request);
            return ResponseEntity.ok(ApiResponse.success(PuntoReciclajeResponse.fromEntity(punto), "Punto de reciclaje actualizado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePunto(@PathVariable Long id) {
        try {
            puntoReciclajeService.deletePunto(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Punto de reciclaje eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PuntoReciclajeResponse>>> searchPuntos(@RequestParam(required = false) String query) {
        try {
            List<PuntoReciclaje> puntos = puntoReciclajeService.searchPuntos(query);
            List<PuntoReciclajeResponse> responses = puntos.stream()
                .map(PuntoReciclajeResponse::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al buscar puntos: " + e.getMessage()));
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<PuntoReciclajeResponse>>> getPuntosByEstado(@PathVariable String estado) {
        try {
            List<PuntoReciclaje> puntos = puntoReciclajeService.getPuntosByEstado(estado);
            List<PuntoReciclajeResponse> responses = puntos.stream()
                .map(PuntoReciclajeResponse::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al obtener puntos por estado: " + e.getMessage()));
        }
    }
}
