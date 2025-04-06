package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.entities.QuartoStandard;
import com.hotel_transylvania.services.ReservaServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaServicesTests {

    private ReservaServices reservaService;

    @BeforeEach
    public void setUp() {
        reservaService = new ReservaServices();

        reservaService.adicionarQuarto(new QuartoStandard(101, 150.0));
        reservaService.adicionarQuarto(new QuartoStandard(102, 180.0));
    }

    @Test
    public void testReservarQuarto() {
        boolean reservado = reservaService.reservarQuarto(101);
        assertTrue(reservado);
        assertFalse(reservaService.buscarPorNumero(101).estaDisponivel());
    }

    @Test
    public void testReservarQuartoIndisponivel() {
        reservaService.reservarQuarto(101);
        boolean reservadoNovamente = reservaService.reservarQuarto(101);
        assertFalse(reservadoNovamente);
    }

    @Test
    public void testLiberarQuarto() {
        reservaService.reservarQuarto(102);
        boolean liberado = reservaService.liberarQuarto(102);
        assertTrue(liberado);
        assertTrue(reservaService.buscarPorNumero(102).estaDisponivel());
    }

    @Test
    public void testListarQuartosDisponiveis() {
        reservaService.reservarQuarto(101);
        assertEquals(1, reservaService.listarQuartosDisponiveis().size());
    }
}
