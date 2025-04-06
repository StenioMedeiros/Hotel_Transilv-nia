package com.hotel_transylvania.exceptions;

public class ServicoExtraJaExisteException extends HotelTransylvaniaException {
    public ServicoExtraJaExisteException(String nome, Long quartoId) {
        super("Já existe um serviço extra com o nome '" + nome + "' para o quarto ID " + quartoId + ".");
    }
}
