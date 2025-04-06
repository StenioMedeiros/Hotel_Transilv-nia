package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.entities.QuartoStandard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuartoStandardTests {

    @Test
    public void testReserva() {
        QuartoStandard quarto = new QuartoStandard(101, 150.0);
        quarto.reservar();
        assertFalse(quarto.estaDisponivel(), "O quarto deveria estar reservado (indisponível)");
    }

    @Test
    public void testLiberar() {
        QuartoStandard quarto = new QuartoStandard(102, 180.0);
        quarto.reservar(); // primeiro ocupa
        quarto.liberar();  // depois libera
        assertTrue(quarto.estaDisponivel(), "O quarto deveria estar liberado (disponível)");
    }

    @Test
    public void testDisponibilidade() {
        QuartoStandard quarto = new QuartoStandard(103, 160.0);
        assertTrue(quarto.estaDisponivel(), "O quarto deveria estar disponível");
    }

    @Test
    public void testCalculoPreco() {
        QuartoStandard quarto = new QuartoStandard(104, 200.0);
        assertEquals(200.0, quarto.calcularPreco(), 0.001, "O preço deveria ser 200.0");
    }

    @Test
    public void testExibirInfo() {
        QuartoStandard quarto = new QuartoStandard(105, 220.0);
        String info = quarto.exibirInfo();
        assertTrue(info.contains("Número: 105"));
        assertTrue(info.contains("Preço: 220.0"));
        assertTrue(info.contains("Tipo: Standard"));
    }
}
