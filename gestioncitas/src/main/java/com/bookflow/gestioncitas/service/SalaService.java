package com.bookflow.gestioncitas.service;

import com.bookflow.gestioncitas.exception.SalaNoEncontradaException;
import com.bookflow.gestioncitas.model.*;
import com.bookflow.gestioncitas.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    // Registrar una sala
    public Sala registrarSala(Sala sala) {
        if (sala == null) {
            throw new IllegalArgumentException("La sala es obligatoria");
        }
        if (sala.getNombre() == null || sala.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la sala es obligatorio");
        }
        if (sala.getCapacidad() == null || sala.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        if (sala.getDescripcion() == null|| sala.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción de la sala es obligatoria");
        }
        sala.setEstado(true);
        return salaRepository.save(sala);
    }

    //buscar por id
    public Sala buscarSalaPorId(Long id) {
        if (id == null || id <= 0) { 
        }
        return salaRepository.findById(id) // esto tiene un optional que contiene el objeto
            .orElseThrow(() -> new SalaNoEncontradaException("No se encontró la sala con ID: " + id));
    }

    // Actualizar una sala
    public Sala actualizarSala(Long id, Sala datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de actualización son obligatorios");
        }
        Sala sala = buscarSalaPorId(id);
        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (datos.getCapacidad() == null || datos.getCapacidad() <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        if (datos.getDescripcion() == null || datos.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        sala.setNombre(datos.getNombre());
        sala.setCapacidad(datos.getCapacidad());
        sala.setDescripcion(datos.getDescripcion());
        return salaRepository.save(sala);
    }

    // Desactivar una sala
    public Sala desactivarSala(Long id) {
        Sala sala = buscarSalaPorId(id);
        sala.setEstado(false);
        return salaRepository.save(sala);
    }

    // Consultar salas por capacidad minima
    public List<Sala> buscarSalasPorCapacidad(Integer capacidadMinima) {
        if (capacidadMinima == null || capacidadMinima <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        return salaRepository.findByCapacidadGreaterThanEqual(capacidadMinima);
    }

    // Verificar que la sala exista y esté activa
    public Sala validarSalaActiva(Long id) {
        Sala sala = buscarSalaPorId(id);
        if (!Boolean.TRUE.equals(sala.getEstado())) {
            throw new IllegalStateException("No se pueden realizar reservas en una sala desactivada");
        }
        return sala;
    }
}