package com.hotel_transylvania.exceptions;

public class ReservaJaCanceladaException extends HotelTransylvaniaException {
    public ReservaJaCanceladaException() {
        super("Esta reserva já foi cancelada anteriormente");
    }
    
    public ReservaJaCanceladaException(String message) {
        super(message);
    }
}
