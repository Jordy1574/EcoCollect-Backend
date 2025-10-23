package com.upn.ecocollect.controller;

import com.upn.ecocollect.dto.ApiResponse;
import com.upn.ecocollect.dto.MaterialRequest;
import com.upn.ecocollect.model.Material;
import com.upn.ecocollect.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Material>>> getAllMateriales() {
        try {
            List<Material> materiales = materialService.getAllMateriales();
            return ResponseEntity.ok(ApiResponse.success(materiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al obtener materiales: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Material>> getMaterialById(@PathVariable Long id) {
        try {
            Material material = materialService.getMaterialById(id);
            return ResponseEntity.ok(ApiResponse.success(material));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Material>> createMaterial(@Valid @RequestBody MaterialRequest request) {
        try {
            Material material = materialService.createMaterial(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(material, "Material creado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Material>> updateMaterial(
            @PathVariable Long id,
            @Valid @RequestBody MaterialRequest request) {
        try {
            Material material = materialService.updateMaterial(id, request);
            return ResponseEntity.ok(ApiResponse.success(material, "Material actualizado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterial(@PathVariable Long id) {
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Material eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Material>>> searchMateriales(@RequestParam(required = false) String query) {
        try {
            List<Material> materiales = materialService.searchMateriales(query);
            return ResponseEntity.ok(ApiResponse.success(materiales));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al buscar materiales: " + e.getMessage()));
        }
    }
}
