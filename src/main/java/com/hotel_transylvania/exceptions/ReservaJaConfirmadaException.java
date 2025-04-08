package com.hotel_transylvania.exceptions;

public class ReservaJaConfirmadaException extends HotelTransylvaniaException {
    public ReservaJaConfirmadaException() {
        super("Esta reserva já foi confirmada anteriormente");
    }
    
    public ReservaJaConfirmadaException(String message) {
        super(message);
    }
}