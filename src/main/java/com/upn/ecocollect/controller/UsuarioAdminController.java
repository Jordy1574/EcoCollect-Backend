package com.upn.ecocollect.controller;

import com.upn.ecocollect.dto.ApiResponse;
import com.upn.ecocollect.dto.UsuarioAdminRequest;
import com.upn.ecocollect.dto.UsuarioResponse;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.service.UsuarioAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/usuarios")
public class UsuarioAdminController {

    @Autowired
    private UsuarioAdminService usuarioAdminService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioAdminService.getAllUsuarios();
            List<UsuarioResponse> responses = usuarios.stream()
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Error al obtener usuarios: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioAdminService.getUsuarioById(id);
            return ResponseEntity.ok(ApiResponse.success(UsuarioResponse.fromEntity(usuario)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> createUsuario(@Valid @RequestBody UsuarioAdminRequest request) {
        try {
            Usuario usuario = usuarioAdminService.createUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(UsuarioResponse.fromEntity(usuario), "Usuario creado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioAdminRequest request) {
        try {
            Usuario usuario = usuarioAdminService.updateUsuario(id, request);
            return ResponseEntity.ok(ApiResponse.success(UsuarioResponse.fromEntity(usuario), "Usuario actualizado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioAdminService.deleteUsuario(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Usuario eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getMessage()));
        }
    }
}
