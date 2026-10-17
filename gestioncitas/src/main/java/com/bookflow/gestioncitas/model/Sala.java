package com.bookflow.gestioncitas.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id;

    @Column(name = "nombre_sala", nullable = false, length = 100)
    private String nombre;

    @Column(name = "capacidad_sala",nullable = false)
    private Integer capacidad;

    @Column(name = "descripcion_sala", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado_sala", nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "sala",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Sala() {
    }

    public Long getId() {
        return id;
    }

    public Sala(String nombre, Integer capacidad, String descripcion, Boolean estado){
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
