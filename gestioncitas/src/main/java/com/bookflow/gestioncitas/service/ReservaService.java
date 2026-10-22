package com.bookflow.gestioncitas.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.bookflow.gestioncitas.exception.ReservaNoEncontradaException;
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
        if (reserva.getNombreCliente() == null || reserva.getNombreCliente().isBlank()) {
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

    //Buscar reserva por fecha especifica
    public List<Reserva> buscarPorFechaEspecifica(LocalDateTime fecha){
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }
        List<Reserva> reservas = reservaRepository.buscarPorFechaEspecifica(fecha);
        return reservas;
    }
    
    //buscar reservas de una sala
    public List<Reserva> reservasDeUnaSala(Long id){
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
        Sala sala = salaService.buscarSalaPorId(id);
        return reservaRepository.findBySalaId(sala.getId());
    }


    //Buscar reserva por ID
    public Reserva buscarReserva(Long id){
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }

        return reservaRepository.findById(id)
            .orElseThrow(() -> new ReservaNoEncontradaException("No se encontro la reserva con el id " + id));
    }

    //Cancelar reserva
    public Reserva cancelarReserva(Long id){
        Reserva reserva = buscarReserva(id);
        reserva.setEstado(false);
        return reserva;
    }
}
