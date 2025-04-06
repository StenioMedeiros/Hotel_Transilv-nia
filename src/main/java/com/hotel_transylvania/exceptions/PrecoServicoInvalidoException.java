package com.hotel_transylvania.exceptions;

public class PrecoServicoInvalidoException extends HotelTransylvaniaException {
    public PrecoServicoInvalidoException() {
        super("O preço do serviço extra deve ser maior que zero.");
    }
}