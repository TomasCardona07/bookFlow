package com.bookflow.gestioncitas.repository;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookflow.gestioncitas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    @Query("""
            SELECT CASE WHEN COUNT(reserva) > 0 THEN true ELSE false END
            FROM Reserva reserva
            WHERE reserva.sala.id = :salaId
            AND reserva.estado = false
            AND reserva.fechaInicio < :fechaFinal
            AND reserva.fechaFinal > :fechaInicio
        """)
    public boolean existeSolapamiento(
        @Param("salaId") Long salaId,
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFinal") LocalDateTime fechaFinal
    );

    List<Reserva> findBySalaId(Long salaId); //reservas de una sala
    

    //Buscar una fecha especifica
    @Query("SELECT reserva FROM Reserva reserva WHERE :fecha BETWEEN reserva.fechaInicio AND reserva.fechaFinal")
    List<Reserva> buscarPorFechaEspecifica(@Param("fecha") LocalDateTime fecha);
}
