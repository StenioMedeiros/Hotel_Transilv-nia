package com.hotel_transylvania.exceptions;

public class DataCheckOutInvalidaException extends HotelTransylvaniaException {
    public DataCheckOutInvalidaException() {
        super("Data de check-out deve ser após check-in");
    }
    
    public DataCheckOutInvalidaException(String message) {
        super(message);
    }
}
