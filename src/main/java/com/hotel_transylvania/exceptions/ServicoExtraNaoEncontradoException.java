package com.hotel_transylvania.exceptions;

public class ServicoExtraNaoEncontradoException extends HotelTransylvaniaException {
    public ServicoExtraNaoEncontradoException(Long id) {
        super("Serviço extra com ID " + id + " não encontrado.");
    }
}