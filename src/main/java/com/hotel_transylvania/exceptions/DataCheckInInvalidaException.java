package com.hotel_transylvania.exceptions;

public class DataCheckInInvalidaException extends HotelTransylvaniaException {
    public DataCheckInInvalidaException() {
        super("Data de check-in não pode ser no passado");
    }
    
    public DataCheckInInvalidaException(String message) {
        super(message);
    }
}
