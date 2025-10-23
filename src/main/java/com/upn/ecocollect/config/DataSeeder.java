package com.upn.ecocollect.config;

import com.upn.ecocollect.model.Material;
import com.upn.ecocollect.model.PuntoReciclaje;
import com.upn.ecocollect.model.RolUsuario;
import com.upn.ecocollect.model.Usuario;
import com.upn.ecocollect.repository.MaterialRepository;
import com.upn.ecocollect.repository.PuntoReciclajeRepository;
import com.upn.ecocollect.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Value("${ecocollect.seed.enabled:false}")
    private boolean seedEnabled;

    @Bean
    public CommandLineRunner seedData(
            MaterialRepository materialRepository,
            UsuarioRepository usuarioRepository,
            PuntoReciclajeRepository puntoReciclajeRepository,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            if (!seedEnabled) {
                log.info("Seeder desactivado. Para activar, establece ecocollect.seed.enabled=true en application.properties");
                return;
            }

            log.info("Iniciando seeder de datos...");

            // 1. Materiales
            if (materialRepository.count() == 0) {
                log.info("Creando materiales de ejemplo...");
                materialRepository.saveAll(List.of(
                    createMaterial("Plástico", 2.5),
                    createMaterial("Papel", 1.8),
                    createMaterial("Vidrio", 1.2),
                    createMaterial("Metal", 4.0),
                    createMaterial("Electrónicos", 5.0),
                    createMaterial("Cartón", 1.4)
                ));
                log.info("6 materiales creados");
            }

            // 2. Usuarios
            if (usuarioRepository.count() == 1) { // Solo existe el admin por defecto
                log.info("Creando usuarios de ejemplo...");
                
                // Recolector
                Usuario recolector = new Usuario();
                recolector.setNombre("Recolector Demo");
                recolector.setEmail("recolector@ecocollect.pe");
                recolector.setPassword(passwordEncoder.encode("Reco123*"));
                recolector.setRol(RolUsuario.RECOLECTOR);
                recolector.setTelefono("988888888");
                recolector.setDireccion("Lima");
                usuarioRepository.save(recolector);

                // Cliente
                Usuario cliente = new Usuario();
                cliente.setNombre("Cliente Demo");
                cliente.setEmail("cliente@ecocollect.pe");
                cliente.setPassword(passwordEncoder.encode("Cliente123*"));
                cliente.setRol(RolUsuario.CLIENTE);
                cliente.setTelefono("977777777");
                cliente.setDireccion("San Isidro");
                usuarioRepository.save(cliente);

                log.info("2 usuarios de ejemplo creados (recolector y cliente)");
            }

            // 3. Puntos de reciclaje
            if (puntoReciclajeRepository.count() == 0) {
                log.info("Creando puntos de reciclaje de ejemplo...");
                
                List<Material> allMaterials = materialRepository.findAll();
                if (allMaterials.size() < 3) {
                    log.warn("No hay suficientes materiales para crear puntos. Saltando...");
                    return;
                }

                // Punto 1: Miraflores
                PuntoReciclaje punto1 = new PuntoReciclaje();
                punto1.setNombre("EcoPoint Miraflores");
                punto1.setDireccion("Av. Larco 345, Miraflores");
                punto1.setTelefono("012345678");
                punto1.setEstado("activo");
                Set<Material> materiales1 = new HashSet<>(allMaterials.subList(0, 3)); // Primeros 3
                punto1.setMaterialesAceptados(materiales1);
                puntoReciclajeRepository.save(punto1);

                // Punto 2: San Isidro
                PuntoReciclaje punto2 = new PuntoReciclaje();
                punto2.setNombre("EcoPoint San Isidro");
                punto2.setDireccion("Av. Javier Prado 567, San Isidro");
                punto2.setTelefono("012345679");
                punto2.setEstado("activo");
                Set<Material> materiales2 = new HashSet<>(allMaterials); // Todos
                punto2.setMaterialesAceptados(materiales2);
                puntoReciclajeRepository.save(punto2);

                log.info("2 puntos de reciclaje creados");
            }

            log.info("Seeder completado exitosamente");
        };
    }

    private Material createMaterial(String nombre, double precio) {
        Material material = new Material();
        material.setNombre(nombre);
        material.setPrecioPorKg(precio);
        return material;
    }
}
