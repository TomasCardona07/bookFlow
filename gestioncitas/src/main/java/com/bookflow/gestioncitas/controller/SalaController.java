package com.bookflow.gestioncitas.controller;
import com.bookflow.gestioncitas.model.*;
import com.bookflow.gestioncitas.repository.*;
import com.bookflow.gestioncitas.service.SalaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaRepository salaRepository;
    private final SalaService salaService;

    public SalaController(SalaRepository salaRepository, SalaService salaService) {
        this.salaRepository = salaRepository;
        this.salaService = salaService;
    }

    // 1. Registrar una sala
    @PostMapping
    public ResponseEntity<Sala> registrarSala(@RequestBody Sala sala) {
        Sala nuevaSala = salaService.registrarSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSala);
    }

    // 2. Consultar todas las salas
    @GetMapping
    public ResponseEntity<List<Sala>> listarSalas() {
        return ResponseEntity.ok(salaRepository.findAll()); //me parece innecesario pasarla por services
    }

    // 3. Consultar una sala por su identificador
    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerSala(@PathVariable Long id) {
        Sala salaEncontrada = salaService.buscarSalaPorId(id);
        return ResponseEntity.ok(salaEncontrada);
    }

    // 4. Actualizar la información de una sala
    @PutMapping("/{id}")
    public ResponseEntity<Sala> actualizarSala(@PathVariable Long id, @RequestBody Sala datosSala) {
        Sala salaActualizada = salaService.actualizarSala(id, datosSala);
        return ResponseEntity.ok(salaActualizada);
    }

    // 5. Desactivar una sala
    @PatchMapping("/{id}/desactivar") //patch porque es un cambio parcial
    public ResponseEntity<Sala> desactivarSala(@PathVariable Long id) {
        Sala salaDesactivada = salaService.desactivarSala(id);
        return ResponseEntity.ok(salaDesactivada);
    }

    // 6. Consultar salas según su capacidad mínima
    @GetMapping("/capacidad")
    public ResponseEntity<List<Sala>> consultarPorCapacidad(@RequestParam Integer minima) {
        List<Sala> salasCapacidad = salaService.buscarSalasPorCapacidad(minima);
        return ResponseEntity.ok(salasCapacidad);
    }

    // 7. Validar que una sala esté activa antes de reservar
    @GetMapping("/{id}/disponible-para-reservas")
    public ResponseEntity<Boolean> validarSalaParaReservas(@PathVariable Long id) {
        Sala salaActiva = salaService.validarSalaActiva(id);
        return ResponseEntity.ok(salaActiva.getEstado());
    }
}
