package com.bookflow.gestioncitas.exception;

public class ReservaNoEncontradaException extends RuntimeException {

    public ReservaNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
