package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoQuarto;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class QuartoStandard extends Quarto {
    
    public QuartoStandard() {
        super();
        this.setTipo(TipoQuarto.STANDARD);
    }

    public QuartoStandard(Integer numero, BigDecimal preco) {
        super(numero, preco, TipoQuarto.STANDARD);
    }

    @Override
    public BigDecimal calcularPrecoTotal() {
        return getPreco();
    }

    @Override
    public String exibirInfo() {
        return String.format("Quarto Standard #%d - Preço: R$%.2f - %s",
                getNumero(), getPreco().doubleValue(), getDisponivel() ? "Disponível" : "Ocupado");
    }
}