package com.hotel_transylvania.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "servicos_extras")
public class ServicoExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private BigDecimal precoAdicional;
    
    @ManyToOne
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    // Construtores
    public ServicoExtra() {
    }

    public ServicoExtra(String nome, BigDecimal precoAdicional) {
        this.nome = nome;
        this.precoAdicional = precoAdicional;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

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

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }
}