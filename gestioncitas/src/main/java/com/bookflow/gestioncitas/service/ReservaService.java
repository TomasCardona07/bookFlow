package com.bookflow.gestioncitas.service;

import org.springframework.stereotype.Service;

import com.bookflow.gestioncitas.model.Reserva;
import com.bookflow.gestioncitas.model.Sala;
import com.bookflow.gestioncitas.repository.ReservaRepository;

@Service 
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final SalaService salaService;

    public ReservaService(ReservaRepository reservaRepository, SalaService salaService){
        this.reservaRepository = reservaRepository;
        this.salaService = salaService;
    }

    //registrar reserva
    public Reserva crearReserva(Reserva reserva){
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede estar vacia");
        }
        if (reserva.getNombreCliente().isBlank() || reserva.getNombreCliente() == null) {
            throw new IllegalArgumentException("El nombre del que reserva es obligatorio");
        }

        //hize esto porque me parecia mejor llamar a SalaService para que valide estado y existencia de la sala
        Sala sala = salaService.validarSalaActiva(reserva.getSala().getId()); 

        if (reserva.getFechaInicio().isAfter(reserva.getFechaFinal())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser despues de la fecha final");
        }
        if (reservaRepository.existeSolapamiento(sala.getId(), reserva.getFechaInicio(), reserva.getFechaFinal())){
            throw new IllegalArgumentException("El horario de la reserva ya tiene la sala ocupada");
        }
        return reservaRepository.save(reserva);
    }
}
