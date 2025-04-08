package com.hotel_transylvania.exceptions;

public class ReservaNaoEncontradaException extends HotelTransylvaniaException {
    public ReservaNaoEncontradaException() {
        super("Reserva não encontrada");
    }
    
    public ReservaNaoEncontradaException(String message) {
        super(message);
    }
}
