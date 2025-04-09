package com.hoteltransylvania.hotel_transylvania;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

// Importe os enums e as classes de entidade conforme os packages reais do seu projeto
import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.QuartoStandard;
import com.hotel_transylvania.enums.StatusReserva;
import com.hotel_transylvania.enums.TipoReserva;


/**
 * Testes unitários para a entidade Reserva.
 */
public class ReservaTests {

    private Hospede hospede;
    private Quarto quarto;
    private Reserva reserva;

    @BeforeEach
    public void setup() {
        // Criação dos stubs para hospede e quarto.
        hospede = new Hospede();
        // Utilizando a implementação de QuartoStandard, configurando o número e o preço
        quarto = new QuartoStandard(101, BigDecimal.valueOf(100.00));

        // Define as datas de check-in e check-out (ex.: check-in amanhã, check-out 3 dias depois)
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
        // Para o TipoReserva, utilize o que for adequado; usamos aqui um exemplo chamado PADRAO
        reserva = new Reserva(hospede, quarto, checkIn, checkOut, TipoReserva.PACOTE);
    }

    @Test
    public void testCalcularTotal() {
        // O cálculo do total é: preço do quarto (via calcularPrecoTotal) multiplicado pelo número de noites.
        long numeroDeNoites = reserva.getDataCheckIn().until(reserva.getDataCheckOut()).getDays();
        BigDecimal valorEsperado = quarto.calcularPrecoTotal().multiply(BigDecimal.valueOf(numeroDeNoites));
        assertEquals(valorEsperado, reserva.calcularTotal(),
                "O valor total deve ser o preço do quarto multiplicado pelo número de noites.");
    }

    @Test
    public void testConfirmarReserva() {
        // Ao confirmar a reserva, espera-se que o status mude para CONFIRMADA
        // e que o quarto seja marcado como indisponível.
        reserva.confirmarReserva();
        assertEquals(StatusReserva.CONFIRMADA, reserva.getStatus(),
                "Após confirmação, o status da reserva deve ser CONFIRMADA.");
        assertFalse(quarto.getDisponivel(),
                "Após confirmação, o quarto não deve estar disponível.");
    }

    @Test
    public void testCancelarReserva() {
        // Primeiro, confirme a reserva para alterar o status
        reserva.confirmarReserva();
        // Agora, ao cancelar, o status deve ser alterado para CANCELADA,
        // e o quarto voltar a ficar disponível.
        reserva.cancelarReserva();
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus(),
                "Após cancelamento, o status da reserva deve ser CANCELADA.");
        assertTrue(quarto.getDisponivel(),
                "Após cancelamento, o quarto deve voltar a estar disponível.");
    }

    @Test
    public void testRecalculaValorTotalAoAlterarDatas() {
        // Verifica se a alteração da data recalcula o valor total da reserva.
        BigDecimal valorInicial = reserva.getValorTotal();
        
        // Altera a data de check-out para acrescentar mais uma noite
        LocalDate novaDataCheckOut = reserva.getDataCheckOut().plusDays(1);
        reserva.setDataCheckOut(novaDataCheckOut);
        
        long novoNumeroNoites = reserva.getDataCheckIn().until(novaDataCheckOut).getDays();
        BigDecimal novoValorEsperado = quarto.calcularPrecoTotal().multiply(BigDecimal.valueOf(novoNumeroNoites));
        
        assertNotEquals(valorInicial, reserva.getValorTotal(),
                "Ao alterar a data de check-out, o valor total deve ser recalculado.");
        assertEquals(novoValorEsperado, reserva.getValorTotal(),
                "O novo valor total deve ser o preço do quarto multiplicado pelo novo número de noites.");
    }

    @Test
    public void testConfirmarReservaNaoDeveConfirmarSeJaConfirmada() {
        // Confirma a reserva e tenta confirmar novamente: o status não deve ser alterado.
        reserva.confirmarReserva();
        reserva.confirmarReserva();
        assertEquals(StatusReserva.CONFIRMADA, reserva.getStatus(),
                "Uma reserva já confirmada não deve modificar o status ao confirmar novamente.");
    }

    @Test
    public void testCancelarReservaNaoDeveCancelarSeJaCancelada() {
        // Cancela a reserva e tenta cancelar novamente: o status não deve ser alterado.
        reserva.cancelarReserva();
        reserva.cancelarReserva();
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus(),
                "Uma reserva já cancelada não deve alterar o status ao ser cancelada novamente.");
    }

}
