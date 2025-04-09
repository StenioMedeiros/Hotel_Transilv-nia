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

/**
 * Classe de testes unitários para a fachada {@link QuartoFachada}.
 * Esta classe testa as interações da fachada com o serviço de quartos,
 * utilizando um serviço fake para isolar os testes.
 */
class QuartoFachadaTests {

    private QuartoFachada quartoFachada; // A fachada que será testada.
    private FakeQuartoService fakeQuartoService; // Um serviço de quarto falso para simular o comportamento do serviço real.
    private QuartoDTO quartoDTO; // Um DTO (Data Transfer Object) para representar os dados de um quarto.

    /**
     * Classe interna que simula o comportamento do serviço {@link QuartoService}.
     * Ela mantém uma lista de quartos em memória e implementa os métodos do serviço
     * de forma controlada para os testes.
     */
    static class FakeQuartoService extends QuartoService {
        private final List<Quarto> quartos = new ArrayList<>(); // Lista para armazenar os quartos simulados.
        private final AtomicLong idGenerator = new AtomicLong(1); // Gerador atômico para simular a geração de IDs.
        private Long idParaLancarExcecao = null; // ID configurável para forçar o lançamento de uma exceção.

        /**
         * Simula a criação de um novo quarto. Atribui um ID único e adiciona o quarto à lista.
         * @param quartoDTO DTO contendo os dados do quarto a ser criado.
         * @return O quarto criado.
         */
        @Override
        public Quarto criarQuarto(QuartoDTO quartoDTO) {
            Quarto quarto = createQuartoByType(quartoDTO); // Cria a instância correta de Quarto (Standard ou Suite).
            setQuartoIdUsingReflection(quarto, idGenerator.getAndIncrement()); // Define o ID do quarto usando reflexão.
            quartos.add(quarto); // Adiciona o quarto à lista.
            return quarto;
        }

        /**
         * Cria uma instância de {@link QuartoStandard} ou {@link QuartoSuite} baseado no tipo fornecido no DTO.
         * @param dto DTO contendo o tipo do quarto.
         * @return A instância do quarto criada.
         */
        private Quarto createQuartoByType(QuartoDTO dto) {
            return switch (dto.getTipo()) {
                case STANDARD -> new QuartoStandard(dto.getNumero(), dto.getPreco());
                case SUITE -> new QuartoSuite(dto.getNumero(), dto.getPreco(), dto.getServicosExtras());
            };
        }

        /**
         * Simula a atualização de um quarto existente. Lança uma exceção se o ID configurado para lançar exceção for encontrado.
         * @param id O ID do quarto a ser atualizado.
         * @param quartoDTO DTO contendo os novos dados do quarto.
         * @return O quarto atualizado.
         * @throws QuartoNaoEncontradoException Se o quarto com o ID fornecido não for encontrado.
         */
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

        /**
         * Simula a exclusão de um quarto. Lança uma exceção se o quarto com o ID fornecido não for encontrado.
         * @param id O ID do quarto a ser deletado.
         * @throws QuartoNaoEncontradoException Se o quarto com o ID fornecido não for encontrado.
         */
        @Override
        public void deletarQuarto(Long id) {
            if (!quartos.removeIf(q -> q.getId().equals(id))) {
                throw new QuartoNaoEncontradoException(id);
            }
        }

        /**
         * Simula a listagem de todos os quartos.
         * @return Uma nova lista contendo todos os quartos simulados.
         */
        @Override
        public List<Quarto> listarTodos() {
            return new ArrayList<>(quartos);
        }

        /**
         * Simula a alteração da disponibilidade de um quarto.
         * @param id O ID do quarto a ter sua disponibilidade alterada.
         * @param disponivel O novo status de disponibilidade.
         * @return O quarto com a disponibilidade atualizada.
         * @throws QuartoNaoEncontradoException Se o quarto com o ID fornecido não for encontrado.
         */
        @Override
        public Quarto alterarDisponibilidade(Long id, Boolean disponivel) {
            Quarto quarto = findQuartoById(id);
            quarto.setDisponivel(disponivel);
            return quarto;
        }

        /**
         * Simula a verificação da disponibilidade de um quarto. Lança uma exceção se o quarto não estiver disponível.
         * @param id O ID do quarto a ser verificado.
         * @throws QuartoNaoEncontradoException Se o quarto com o ID fornecido não for encontrado.
         * @throws QuartoNaoDisponivelException Se o quarto estiver marcado como não disponível.
         */
        @Override
        public void verificarDisponibilidade(Long id) {
            Quarto quarto = findQuartoById(id);
            if (!quarto.getDisponivel()) {
                throw new QuartoNaoDisponivelException(id);
            }
        }

        /**
         * Helper method para encontrar um quarto pelo seu ID.
         * @param id O ID do quarto a ser encontrado.
         * @return O quarto encontrado.
         * @throws QuartoNaoEncontradoException Se nenhum quarto com o ID fornecido for encontrado.
         */
        private Quarto findQuartoById(Long id) {
            return quartos.stream()
                    .filter(q -> q.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new QuartoNaoEncontradoException(id));
        }

        /**
         * Configura um ID específico para que as operações de atualização lancem uma exceção.
         * Útil para testar cenários de quarto não encontrado.
         * @param id O ID para o qual a exceção deve ser lançada.
         */
        void configurarExcecaoParaId(Long id) {
            idParaLancarExcecao = id;
        }

        /**
         * Sincroniza o gerador de IDs com o maior ID atualmente presente na lista de quartos.
         * Isso é importante para garantir que novos quartos recebam IDs únicos após a configuração inicial.
         */
        void sincronizarIdGenerator() {
            long maxId = quartos.stream()
                    .mapToLong(Quarto::getId)
                    .max()
                    .orElse(0L);
            idGenerator.set(maxId + 1);
        }

        /**
         * Utiliza reflexão para definir o ID de um quarto. Isso é necessário porque o ID geralmente
         * seria gerado pelo banco de dados e não deve ser diretamente configurado no teste.
         * @param quarto O quarto cujo ID será definido.
         * @param id O ID a ser definido.
         * @throws RuntimeException Se ocorrer um erro ao acessar ou modificar o campo 'id' via reflexão.
         */
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

    /**
     * Método executado antes de cada teste. Inicializa a fachada com o serviço fake
     * e cria um DTO de quarto padrão para ser usado nos testes. Também inicializa
     * a lista de quartos do serviço fake com um quarto padrão.
     */
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

    /**
     * Testa a operação de atualizar um quarto existente.
     * Verifica se o método retorna o quarto atualizado e se o ID permanece o mesmo.
     */
    @Test
    void atualizarQuarto_ComIdExistente_DeveRetornarQuartoAtualizado() {
        Quarto resultado = quartoFachada.atualizarQuarto(1L, quartoDTO);
        assertEquals(1L, resultado.getId());
    }

    /**
     * Testa a operação de remover um quarto.
     * Verifica se o quarto é removido da lista de quartos do serviço fake.
     */
    @Test
    void removerQuarto_DeveRemoverDaLista() {
        quartoFachada.removerQuarto(1L);
        assertTrue(fakeQuartoService.quartos.isEmpty());
    }

    /**
     * Testa a operação de alterar a disponibilidade de um quarto.
     * Verifica se o status de disponibilidade do quarto é atualizado corretamente.
     */
    @Test
    void alterarDisponibilidade_DeveAtualizarStatus() {
        Quarto quarto = quartoFachada.alterarDisponibilidade(1L, false);
        assertFalse(quarto.getDisponivel());
    }

    /**
     * Testa a operação de verificar a disponibilidade de um quarto quando ele está indisponível.
     * Verifica se a exceção {@link QuartoNaoDisponivelException} é lançada corretamente.
     */
    @Test
    void verificarDisponibilidade_QuandoIndisponivel_DeveLancarExcecao() {
        fakeQuartoService.quartos.get(0).setDisponivel(false);
        assertThrows(QuartoNaoDisponivelException.class,
            () -> quartoFachada.verificarDisponibilidade(1L));
    }

    /**
     * Testa a operação de listar todos os quartos.
     * Verifica se o método retorna uma lista com o número correto de quartos
     * e se o ID do quarto na lista corresponde ao esperado.
     */
    @Test
    void listarTodosQuartos_DeveRetornarListaCorreta() {
        List<Quarto> quartos = quartoFachada.listarTodosQuartos();
        assertEquals(1, quartos.size());
        assertEquals(1L, quartos.get(0).getId());
    }
}