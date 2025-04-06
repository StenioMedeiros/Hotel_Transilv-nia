package com.hotel_transylvania.exceptions;

public class NumeroQuartoJaExisteException extends HotelTransylvaniaException {
    public NumeroQuartoJaExisteException(Integer numero) {
        super("Já existe um quarto com o número " + numero + ".");
    }
}
