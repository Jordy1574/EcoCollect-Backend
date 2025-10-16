package com.upn.ecocollect.controller;

import com.upn.ecocollect.dto.LoginRequest;
import com.upn.ecocollect.dto.RegistroRequest;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth") // Todos los endpoints de autenticación irán aquí
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint: POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    // @Valid activa las anotaciones de verificación del DTO (NotBlank, Email, etc.)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistroRequest registroRequest) {
        
        try {
            Usuario nuevoUsuario = authService.registrarNuevoUsuario(registroRequest);
            // Retorna una respuesta 201 (Created)
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            // Retorna una respuesta 400 (Bad Request) si el email ya existe
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
     // Endpoint 2: LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String jwt = authService.autenticarUsuario(loginRequest);
            
            // Retorna un objeto JSON con el token
            return ResponseEntity.ok(Map.of("token", jwt));
            
        } catch (RuntimeException e) {
            // Retorna 401 Unauthorized
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}