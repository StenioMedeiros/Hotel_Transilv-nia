package com.hotel_transylvania.exceptions;

public class ReservaConflitanteException extends HotelTransylvaniaException {
    public ReservaConflitanteException() {
        super("Já existe uma reserva para este quarto no período solicitado");
    }
    
    public ReservaConflitanteException(String message) {
        super(message);
    }
}