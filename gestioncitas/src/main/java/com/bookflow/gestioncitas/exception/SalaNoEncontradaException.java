package com.bookflow.gestioncitas.exception;

public class SalaNoEncontradaException extends RuntimeException {
    
    public SalaNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
