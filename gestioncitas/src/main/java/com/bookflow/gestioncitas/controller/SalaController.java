package com.bookflow.gestioncitas.controller;
import com.bookflow.gestioncitas.model.*;
import com.bookflow.gestioncitas.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaRepository salaRepository;

    public SalaController(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    // 1. Registrar una sala
    @PostMapping
    public ResponseEntity<Sala> registrarSala(@RequestBody Sala sala) {
        sala.setEstado(true); //atributo por defecto(se hace en service)
        Sala nuevaSala = salaRepository.save(sala); //validar con services
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSala);
    }

    // 2. Consultar todas las salas
    @GetMapping
    public ResponseEntity<List<Sala>> listarSalas() {
        return ResponseEntity.ok(salaRepository.findAll());
    }

    // 3. Consultar una sala por su identificador
    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerSala(@PathVariable Long id) {
        return salaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Actualizar la información de una sala
    @PutMapping("/{id}")
    public ResponseEntity<Sala> actualizarSala(@PathVariable Long id, @RequestBody Sala datosSala) {
        return salaRepository.findById(id)
                .map(sala -> {
                    sala.setNombre(datosSala.getNombre());
                    sala.setCapacidad(datosSala.getCapacidad());
                    sala.setDescripcion(datosSala.getDescripcion());
                    Sala actualizada = salaRepository.save(sala);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Desactivar una sala
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Sala> desactivarSala(@PathVariable Long id) {
        return salaRepository.findById(id)
                .map(sala -> {
                    sala.setEstado(false);
                    Sala desactivada = salaRepository.save(sala);
                    return ResponseEntity.ok(desactivada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 6. Consultar salas según su capacidad mínima
    @GetMapping("/capacidad")
    public ResponseEntity<List<Sala>> consultarPorCapacidad(
            @RequestParam Integer minima) {

        List<Sala> salas = salaRepository
                .findByCapacidadGreaterThanEqual(minima);

        return ResponseEntity.ok(salas);
    }

    // 7. Validar que una sala esté activa antes de reservar
    @GetMapping("/{id}/disponible-para-reservas")
    public ResponseEntity<Boolean> validarSalaParaReservas(
            @PathVariable Long id) {

        return salaRepository.findById(id)
                .map(sala -> ResponseEntity.ok(
                        Boolean.TRUE.equals(sala.getEstado())))
                .orElse(ResponseEntity.notFound().build());
    }
}
