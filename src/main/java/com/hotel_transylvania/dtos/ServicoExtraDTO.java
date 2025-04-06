package com.hotel_transylvania.dtos;


import java.math.BigDecimal;

public class ServicoExtraDTO {
    private String nome;
    private BigDecimal precoAdicional;
    private Long quartoId;

    // Construtores
    public ServicoExtraDTO() {
    }

    public ServicoExtraDTO(String nome, BigDecimal precoAdicional, Long quartoId) {
        this.nome = nome;
        this.precoAdicional = precoAdicional;
        this.quartoId = quartoId;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoAdicional() {
        return precoAdicional;
    }

    public void setPrecoAdicional(BigDecimal precoAdicional) {
        this.precoAdicional = precoAdicional;
    }

    public Long getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(Long quartoId) {
        this.quartoId = quartoId;
    }
}