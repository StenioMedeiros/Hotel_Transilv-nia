package com.hotel_transylvania.exceptions;

public class QuartoNaoDisponivelException extends HotelTransylvaniaException {
    public QuartoNaoDisponivelException() {
        super("Quarto não está disponível para reserva");
    }
    
    public QuartoNaoDisponivelException(String message) {
        super(message);
    }
}