package com.hotel_transylvania.exceptions;

public class DatasReservaObrigatoriasException extends HotelTransylvaniaException {
    public DatasReservaObrigatoriasException() {
        super("Datas de check-in e check-out são obrigatórias");
    }
    
    public DatasReservaObrigatoriasException(String message) {
        super(message);
    }
}