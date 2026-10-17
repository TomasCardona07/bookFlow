package com.bookflow.gestioncitas.repository;
import com.bookflow.gestioncitas.model.Sala;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala,Long> {
    List<Sala> findByCapacidadGreaterThanEqual(Integer capacidad);
}
