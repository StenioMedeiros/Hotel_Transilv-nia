package com.hotel_transylvania.services;

import com.hotel_transylvania.dtos.ServicoExtraDTO;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.ServicoExtra;
import com.hotel_transylvania.exceptions.*;
import com.hotel_transylvania.repositories.QuartoRepository;
import com.hotel_transylvania.repositories.ServicoExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicoExtraService {

    @Autowired
    private ServicoExtraRepository servicoExtraRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    public ServicoExtra criarServicoExtra(ServicoExtraDTO servicoExtraDTO) {
        validarDadosServico(servicoExtraDTO);
        Quarto quarto = obterQuartoValido(servicoExtraDTO.getQuartoId());
        
        ServicoExtra servico = new ServicoExtra(
                servicoExtraDTO.getNome(),
                servicoExtraDTO.getPrecoAdicional());
        servico.setQuarto(quarto);
        
        return servicoExtraRepository.save(servico);
    }

    private void validarDadosServico(ServicoExtraDTO dto) {
        if (dto.getPrecoAdicional().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PrecoServicoInvalidoException();
        }
        
        if (dto.getQuartoId() != null && servicoExtraRepository.existsByNomeAndQuartoId(dto.getNome(), dto.getQuartoId())) {
            throw new ServicoExtraJaExisteException(dto.getNome(), dto.getQuartoId());
        }
    }

    private Quarto obterQuartoValido(Long quartoId) {
        return quartoRepository.findById(quartoId)
                .orElseThrow(() -> new QuartoNaoEncontradoException(quartoId));
    }

    public List<ServicoExtra> listarPorQuarto(Long quartoId) {
        if (!quartoRepository.existsById(quartoId)) {
            throw new QuartoNaoEncontradoException(quartoId);
        }
        return servicoExtraRepository.findByQuartoId(quartoId);
    }

    public ServicoExtra buscarPorId(Long id) {
        return servicoExtraRepository.findById(id)
                .orElseThrow(() -> new ServicoExtraNaoEncontradoException(id));
    }

    public ServicoExtra atualizarServicoExtra(Long id, ServicoExtraDTO dto) {
        return servicoExtraRepository.findById(id)
                .map(servico -> {
                    validarAtualizacaoServico(servico, dto);
                    servico.setNome(dto.getNome());
                    servico.setPrecoAdicional(dto.getPrecoAdicional());
                    return servicoExtraRepository.save(servico);
                })
                .orElseThrow(() -> new ServicoExtraNaoEncontradoException(id));
    }

    private void validarAtualizacaoServico(ServicoExtra servico, ServicoExtraDTO dto) {
        if (!servico.getNome().equals(dto.getNome())) {
            if (servicoExtraRepository.existsByNomeAndQuartoId(dto.getNome(), servico.getQuarto().getId())) {
                throw new ServicoExtraJaExisteException(dto.getNome(), servico.getQuarto().getId());
            }
        }
        
        if (dto.getPrecoAdicional().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PrecoServicoInvalidoException();
        }
    }

    public void deletarServicoExtra(Long id) {
        if (!servicoExtraRepository.existsById(id)) {
            throw new ServicoExtraNaoEncontradoException(id);
        }
        servicoExtraRepository.deleteById(id);
    }
}