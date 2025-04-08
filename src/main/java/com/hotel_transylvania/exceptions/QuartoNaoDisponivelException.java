package com.hotel_transylvania.exceptions;

public class QuartoNaoDisponivelException extends HotelTransylvaniaException {
    public QuartoNaoDisponivelException(Long id) {
        super("Quarto com ID " + id + " não está disponível para reserva.");
    }
    public QuartoNaoDisponivelException() {
        super("Quarto  não está disponível para reserva.");
    }
}