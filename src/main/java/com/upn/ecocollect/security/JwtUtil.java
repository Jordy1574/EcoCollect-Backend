package com.upn.ecocollect.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Genera una clave segura para la firma
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
    // Tiempo de expiración del token (8 horas en milisegundos)
    private final int jwtExpirationMs = 1000 * 60 * 60 * 8; 

    // Genera el JWT
    public String generateToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email) // El usuario (email)
                .claim("rol", rol) // Información extra que el frontend puede leer
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                // Firma el token con la clave secreta
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    // NOTA: El método para validar el token (validateToken) se implementará después.
}