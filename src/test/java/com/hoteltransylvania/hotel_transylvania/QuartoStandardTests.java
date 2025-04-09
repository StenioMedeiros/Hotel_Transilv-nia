package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.entities.QuartoStandard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class QuartoStandardTests {

    @Test
    public void testReserva() {
        QuartoStandard quarto = new QuartoStandard(101, BigDecimal.valueOf(150));
        quarto.setDisponivel(false);
        assertFalse(quarto.getDisponivel(), "O quarto deveria estar reservado (indisponível)");
    }

    @Test
    public void testLiberar() {
        QuartoStandard quarto = new QuartoStandard(102, BigDecimal.valueOf(180.0));
        quarto.setDisponivel(false); // primeiro ocupa
        quarto.setDisponivel(true);  // depois libera
        assertTrue(quarto.getDisponivel(), "O quarto deveria estar liberado (disponível)");
    }

    @Test
    public void testDisponibilidade() {
        QuartoStandard quarto = new QuartoStandard(103, BigDecimal.valueOf(160.0));
        assertTrue(quarto.getDisponivel(), "O quarto deveria estar disponível");
    }

    @Test
    public void testCalculoPreco() {
        QuartoStandard quarto = new QuartoStandard(104, BigDecimal.valueOf(200.0));
        assertEquals(quarto.calcularPrecoTotal(), BigDecimal.valueOf(200.0));
    }

    @Test
    public void testExibirInfo() {
        QuartoStandard quarto = new QuartoStandard(105, BigDecimal.valueOf(220.0));
        String info = quarto.exibirInfo();
        assertEquals(info,"Quarto Standard #105 - Preço: R$220,00 - Disponível");
    }
}
