package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.entities.QuartoSuite;
import com.hotel_transylvania.entities.ServicoExtra;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuartoSuiteTests {

    @Test
    public void testReserva() {
        QuartoSuite suite = new QuartoSuite(201, BigDecimal.valueOf(300.0), Collections.emptyList());
        suite.setDisponivel(false);
        assertFalse(suite.getDisponivel(), "O quarto suite deveria estar reservado (indisponível)");
    }

    @Test
    public void testLiberar() {
        QuartoSuite suite = new QuartoSuite(202, BigDecimal.valueOf(350.0), Collections.emptyList());
        suite.setDisponivel(false);
        suite.setDisponivel(true);
        assertTrue(suite.getDisponivel(), "O quarto suite deveria estar liberado (disponível)");
    }

    @Test
    public void testDisponibilidade() {
        QuartoSuite suite = new QuartoSuite(203, BigDecimal.valueOf(400.0), Collections.emptyList());
        assertTrue(suite.getDisponivel(), "O quarto suite deveria estar disponível");
    }

    @Test
    public void testCalculoPrecoComExtras() {
        ServicoExtra servico = new ServicoExtra("Hidromassagem", BigDecimal.valueOf(200));
        List<ServicoExtra> extra = new ArrayList<>();
        extra.add(servico);
        QuartoSuite suite = new QuartoSuite(204, BigDecimal.valueOf(500.0), extra);
        double precoEsperado = 700.0;
        assertEquals(precoEsperado, suite.calcularPrecoTotal().doubleValue(), 0.001, "Preço com extras deve ser 700.0");
    }

    @Test
    public void testExibirInfo() {
        ServicoExtra servico1 = new ServicoExtra("Hidromassagem", BigDecimal.valueOf(200));
        List<ServicoExtra> extra1 = new ArrayList<>();
        extra1.add(servico1);
        QuartoSuite suite = new QuartoSuite(205, BigDecimal.valueOf(450.0), extra1);
        suite.setDisponivel(true);
        String info = suite.exibirInfo();
        assertEquals("Suíte #205 - Preço Base: R$450,00 - Serviços: Hidromassagem - Disponível", info);
    }
}
