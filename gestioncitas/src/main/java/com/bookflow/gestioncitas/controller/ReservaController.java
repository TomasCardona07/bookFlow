package com.bookflow.gestioncitas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookflow.gestioncitas.model.Reserva;
import com.bookflow.gestioncitas.repository.ReservaRepository;
import com.bookflow.gestioncitas.service.ReservaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController 
@RequestMapping ("api/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final ReservaService reservaService;

    public ReservaController(ReservaRepository reservaRepository, ReservaService reservaService){
        this.reservaRepository = reservaRepository;
        this.reservaService = reservaService;
    }

    //consultar reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> consultarReservas(){
        List<Reserva> reservas = reservaRepository.findAll();
        return ResponseEntity.ok(reservas);
    }

    //crear reserva
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva ){
        Reserva reservaCreada = reservaService.crearReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaCreada); // HTTP 201
    }
}
