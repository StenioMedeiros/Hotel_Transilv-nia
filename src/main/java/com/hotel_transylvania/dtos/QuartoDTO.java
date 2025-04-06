package com.hotel_transylvania.dtos;

import com.hotel_transylvania.entities.ServicoExtra;
import com.hotel_transylvania.enums.TipoQuarto;

import java.math.BigDecimal;
import java.util.List;

public class QuartoDTO {
    private Integer numero;
    private BigDecimal preco;
    private TipoQuarto tipo;
    private Boolean disponivel;
    private List<ServicoExtra> servicosExtras;

    // Getters e Setters
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public List<ServicoExtra> getServicosExtras() {
        return servicosExtras;
    }

    public void setServicosExtras(List<ServicoExtra> servicosExtras) {
        this.servicosExtras = servicosExtras;
    }
}