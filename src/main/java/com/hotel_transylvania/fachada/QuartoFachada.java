package com.hotel_transylvania.fachada;

import com.hotel_transylvania.dtos.QuartoDTO;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.enums.TipoQuarto;
import com.hotel_transylvania.exceptions.QuartoNaoDisponivelException;
import com.hotel_transylvania.exceptions.QuartoNaoEncontradoException;
import com.hotel_transylvania.services.QuartoService;

import java.util.List;

public class QuartoFachada {

    private QuartoService quartoService;

    public QuartoFachada(QuartoService quartoService) {
        this.quartoService = quartoService;
    }


    public Quarto criarQuarto(QuartoDTO quartoDTO) {
        return quartoService.criarQuarto(quartoDTO);
    }

    public Quarto atualizarQuarto(Long id, QuartoDTO quartoDTO) throws QuartoNaoEncontradoException {
        return quartoService.atualizarQuarto(id, quartoDTO);
    }


    public void removerQuarto(Long id) throws QuartoNaoEncontradoException {
        quartoService.deletarQuarto(id);
    }


    public Quarto alterarDisponibilidade(Long id, Boolean disponivel) throws QuartoNaoEncontradoException {
        return quartoService.alterarDisponibilidade(id, disponivel);
    }


    public void verificarDisponibilidade(Long id) throws QuartoNaoEncontradoException, QuartoNaoDisponivelException {
        quartoService.verificarDisponibilidade(id);
    }


    public List<Quarto> listarTodosQuartos() {
        return quartoService.listarTodos();
    }


    public List<Quarto> listarQuartosPorTipo(TipoQuarto tipo) {
        return quartoService.listarPorTipo(tipo);
    }
}
