package com.hotel_transylvania.exceptions;

public class PrecoQuartoInvalidoException extends HotelTransylvaniaException {
    public PrecoQuartoInvalidoException() {
        super("O preço do quarto deve ser maior que zero.");
    }
}