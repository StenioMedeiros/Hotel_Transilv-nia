package com.hotel_transylvania.services;

import com.hotel_transylvania.dtos.QuartoDTO;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.QuartoStandard;
import com.hotel_transylvania.entities.QuartoSuite;
import com.hotel_transylvania.enums.TipoQuarto;
import com.hotel_transylvania.exceptions.*;
import com.hotel_transylvania.repositories.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public List<Quarto> listarPorDisponibilidade(Boolean disponivel) {
        return quartoRepository.findByDisponivel(disponivel);
    }

    public List<Quarto> listarPorTipo(TipoQuarto tipo) {
        return quartoRepository.findByTipo(tipo);
    }

    public Quarto criarQuarto(QuartoDTO quartoDTO) {
        validarDadosQuarto(quartoDTO);
        
        Quarto quarto = switch (quartoDTO.getTipo()) {
            case SUITE -> new QuartoSuite(
                    quartoDTO.getNumero(),
                    quartoDTO.getPreco(),
                    quartoDTO.getServicosExtras() != null ? quartoDTO.getServicosExtras() : new ArrayList<>());
            case STANDARD -> new QuartoStandard(
                    quartoDTO.getNumero(),
                    quartoDTO.getPreco());
        };
        
        return quartoRepository.save(quarto);
    }

    private void validarDadosQuarto(QuartoDTO quartoDTO) {
        if (quartoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PrecoQuartoInvalidoException();
        }
        
        if (quartoRepository.existsByNumero(quartoDTO.getNumero())) {
            throw new NumeroQuartoJaExisteException(quartoDTO.getNumero());
        }
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto atualizarQuarto(Long id, QuartoDTO quartoDTO) {
        return quartoRepository.findById(id)
                .map(quarto -> {
                    validarAtualizacaoQuarto(quarto, quartoDTO);
                    atualizarCamposQuarto(quarto, quartoDTO);
                    return quartoRepository.save(quarto);
                })
                .orElseThrow(() -> new QuartoNaoEncontradoException(id));
    }

    private void validarAtualizacaoQuarto(Quarto quarto, QuartoDTO quartoDTO) {
        if (!quarto.getNumero().equals(quartoDTO.getNumero()) && 
            quartoRepository.existsByNumero(quartoDTO.getNumero())) {
            throw new NumeroQuartoJaExisteException(quartoDTO.getNumero());
        }
        
        if (quartoDTO.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PrecoQuartoInvalidoException();
        }
    }

    private void atualizarCamposQuarto(Quarto quarto, QuartoDTO quartoDTO) {
        quarto.setNumero(quartoDTO.getNumero());
        quarto.setPreco(quartoDTO.getPreco());
        quarto.setDisponivel(quartoDTO.getDisponivel());
        
        if (quarto instanceof QuartoSuite suite && quartoDTO.getServicosExtras() != null) {
            suite.setServicosExtras(quartoDTO.getServicosExtras());
        }
    }

    public void deletarQuarto(Long id) {
        if (!quartoRepository.existsById(id)) {
            throw new QuartoNaoEncontradoException(id);
        }
        quartoRepository.deleteById(id);
    }

    public Quarto alterarDisponibilidade(Long id, Boolean disponivel) {
        return quartoRepository.findById(id)
                .map(quarto -> {
                    quarto.setDisponivel(disponivel);
                    return quartoRepository.save(quarto);
                })
                .orElseThrow(() -> new QuartoNaoEncontradoException(id));
    }
    
    public void verificarDisponibilidade(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new QuartoNaoEncontradoException(id));
                
        if (!quarto.getDisponivel()) {
            throw new QuartoNaoDisponivelException(id);
        }
    }
}


