package com.hotel_transylvania.exceptions;

public class QuartoNaoEncontradoException  extends HotelTransylvaniaException {
    public QuartoNaoEncontradoException(Long id) {
        super("Quarto com ID " + id + " não encontrado.");
    }

}
