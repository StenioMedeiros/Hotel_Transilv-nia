package com.hotel_transylvania.entities;

//import java.util.List;

public class Hotel {
    private String nome;
    private String endereco;
    private String telefone;

    // Associações (opcional, conforme precisar)
    // private List<Quarto> quartos;
    // private List<EventoHotel> eventos;Ver lista de eventos

    public Hotel() {}

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
