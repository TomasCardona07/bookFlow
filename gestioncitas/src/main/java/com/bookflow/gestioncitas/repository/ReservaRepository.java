package com.bookflow.gestioncitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookflow.gestioncitas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    
}
