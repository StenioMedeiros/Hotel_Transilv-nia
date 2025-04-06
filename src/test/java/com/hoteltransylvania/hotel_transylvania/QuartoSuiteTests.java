package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.entities.QuartoSuite;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class QuartoSuiteTests {

    @Test
    public void testReserva() {
        QuartoSuite suite = new QuartoSuite(201, 300.0, Collections.emptyList());
        suite.reservar();
        assertFalse(suite.estaDisponivel(), "O quarto suite deveria estar reservado (indisponível)");
    }

    @Test
    public void testLiberar() {
        QuartoSuite suite = new QuartoSuite(202, 350.0, Collections.emptyList());
        suite.reservar(); // simula quarto ocupado
        suite.liberar();  // agora libera
        assertTrue(suite.estaDisponivel(), "O quarto suite deveria estar liberado (disponível)");
    }

    @Test
    public void testDisponibilidade() {
        QuartoSuite suite = new QuartoSuite(203, 400.0, Collections.emptyList());
        assertTrue(suite.estaDisponivel(), "O quarto suite deveria estar disponível");
    }

    @Test
    public void testCalculoPrecoComExtras() {
        QuartoSuite suite = new QuartoSuite(204, 500.0, Arrays.asList("Jacuzzi", "Café da manhã"));
        double precoEsperado = 500.0 + 2 * 50.0;
        assertEquals(precoEsperado, suite.calcularPreco(), 0.001, "Preço com extras deve ser 600.0");
    }

    @Test
    public void testExibirInfo() {
        QuartoSuite suite = new QuartoSuite(205, 450.0, Arrays.asList("Banheira", "Wi-Fi"));
        String info = suite.exibirInfo();
        assertTrue(info.contains("Número: 205"));
        assertTrue(info.contains("Preço:"));
        assertTrue(info.contains("Tipo: Suite"));
        assertTrue(info.contains("Banheira"));
        assertTrue(info.contains("Wi-Fi"));
    }
}
