package com.bookflow.gestioncitas.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookflow.gestioncitas.model.Reserva;
import com.bookflow.gestioncitas.repository.ReservaRepository;
import com.bookflow.gestioncitas.service.ReservaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/{fecha}/buscar-reserva")
    public ResponseEntity<List<Reserva>> buscarPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fecha) {
        List<Reserva> reservasEncontradas = reservaService.buscarPorFechaEspecifica(fecha);
        return ResponseEntity.ok(reservasEncontradas);
    }

    @GetMapping("/{idSala}/reservas-sala")
    public ResponseEntity<List<Reserva>> reservasDeUnaSala(@PathVariable Long idSala) {
        reservaService.reservasDeUnaSala(idSala);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{id}/cancelar-reserva")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id){
        Reserva reserva = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(reserva);
    }
    
}
