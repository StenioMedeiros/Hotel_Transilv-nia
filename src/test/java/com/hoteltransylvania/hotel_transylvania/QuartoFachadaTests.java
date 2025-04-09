package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.dtos.QuartoDTO;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.QuartoStandard;
import com.hotel_transylvania.entities.QuartoSuite;
import com.hotel_transylvania.enums.TipoQuarto;
import com.hotel_transylvania.exceptions.QuartoNaoDisponivelException;
import com.hotel_transylvania.exceptions.QuartoNaoEncontradoException;
import com.hotel_transylvania.fachada.QuartoFachada;
import com.hotel_transylvania.services.QuartoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;


class QuartoFachadaTests {

    private QuartoFachada quartoFachada; // A fachada que será testada.
    private FakeQuartoService fakeQuartoService; // Um serviço de quarto falso para simular o comportamento do serviço real.
    private QuartoDTO quartoDTO; // Um DTO (Data Transfer Object) para representar os dados de um quarto.


    static class FakeQuartoService extends QuartoService {
        private final List<Quarto> quartos = new ArrayList<>(); // Lista para armazenar os quartos simulados.
        private final AtomicLong idGenerator = new AtomicLong(1); // Gerador atômico para simular a geração de IDs.
        private Long idParaLancarExcecao = null; // ID configurável para forçar o lançamento de uma exceção.


        @Override
        public Quarto criarQuarto(QuartoDTO quartoDTO) {
            Quarto quarto = createQuartoByType(quartoDTO); // Cria a instância correta de Quarto (Standard ou Suite).
            setQuartoIdUsingReflection(quarto, idGenerator.getAndIncrement()); // Define o ID do quarto usando reflexão.
            quartos.add(quarto); // Adiciona o quarto à lista.
            return quarto;
        }

 
        private Quarto createQuartoByType(QuartoDTO dto) {
            return switch (dto.getTipo()) {
                case STANDARD -> new QuartoStandard(dto.getNumero(), dto.getPreco());
                case SUITE -> new QuartoSuite(dto.getNumero(), dto.getPreco(), dto.getServicosExtras());
            };
        }

 
        @Override
        public Quarto atualizarQuarto(Long id, QuartoDTO quartoDTO) {
            if (id.equals(idParaLancarExcecao)) {
                throw new QuartoNaoEncontradoException(id);
            }
            return quartos.stream()
                    .filter(q -> q.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new QuartoNaoEncontradoException(id));
        }


        @Override
        public void deletarQuarto(Long id) {
            if (!quartos.removeIf(q -> q.getId().equals(id))) {
                throw new QuartoNaoEncontradoException(id);
            }
        }


        @Override
        public List<Quarto> listarTodos() {
            return new ArrayList<>(quartos);
        }

        @Override
        public Quarto alterarDisponibilidade(Long id, Boolean disponivel) {
            Quarto quarto = findQuartoById(id);
            quarto.setDisponivel(disponivel);
            return quarto;
        }


        @Override
        public void verificarDisponibilidade(Long id) {
            Quarto quarto = findQuartoById(id);
            if (!quarto.getDisponivel()) {
                throw new QuartoNaoDisponivelException(id);
            }
        }

 
        private Quarto findQuartoById(Long id) {
            return quartos.stream()
                    .filter(q -> q.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new QuartoNaoEncontradoException(id));
        }


        void configurarExcecaoParaId(Long id) {
            idParaLancarExcecao = id;
        }


        void sincronizarIdGenerator() {
            long maxId = quartos.stream()
                    .mapToLong(Quarto::getId)
                    .max()
                    .orElse(0L);
            idGenerator.set(maxId + 1);
        }


        private void setQuartoIdUsingReflection(Quarto quarto, Long id) {
            try {
                Field idField = Quarto.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(quarto, id);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao definir ID via reflexão", e);
            }
        }
    }


    @BeforeEach
    void setUp() {
        fakeQuartoService = new FakeQuartoService();
        quartoFachada = new QuartoFachada(fakeQuartoService);

        quartoDTO = new QuartoDTO();
        quartoDTO.setNumero(101);
        quartoDTO.setPreco(BigDecimal.valueOf(250.0));
        quartoDTO.setTipo(TipoQuarto.STANDARD);


        Quarto initialQuarto = new QuartoStandard(101, BigDecimal.valueOf(250.0));
        fakeQuartoService.setQuartoIdUsingReflection(initialQuarto, 1L);
        fakeQuartoService.quartos.add(initialQuarto);
        fakeQuartoService.sincronizarIdGenerator();
    }


    @Test
    void atualizarQuarto_ComIdExistente_DeveRetornarQuartoAtualizado() {
        Quarto resultado = quartoFachada.atualizarQuarto(1L, quartoDTO);
        assertEquals(1L, resultado.getId());
    }

 
    @Test
    void removerQuarto_DeveRemoverDaLista() {
        quartoFachada.removerQuarto(1L);
        assertTrue(fakeQuartoService.quartos.isEmpty());
    }


    @Test
    void alterarDisponibilidade_DeveAtualizarStatus() {
        Quarto quarto = quartoFachada.alterarDisponibilidade(1L, false);
        assertFalse(quarto.getDisponivel());
    }


    @Test
    void verificarDisponibilidade_QuandoIndisponivel_DeveLancarExcecao() {
        fakeQuartoService.quartos.get(0).setDisponivel(false);
        assertThrows(QuartoNaoDisponivelException.class,
            () -> quartoFachada.verificarDisponibilidade(1L));
    }


    @Test
    void listarTodosQuartos_DeveRetornarListaCorreta() {
        List<Quarto> quartos = quartoFachada.listarTodosQuartos();
        assertEquals(1, quartos.size());
        assertEquals(1L, quartos.get(0).getId());
    }
}