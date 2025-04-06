package com.hotel_transylvania.entities;

public class QuartoStandard extends Quarto {

    private String tipo;

    public QuartoStandard(int numero, double preco) {
        super(numero, preco);
        this.tipo = "Standard";
    }

    @Override
    public double calcularPreco() {
        return getPreco();
    }

    @Override
    public String exibirInfo() {
        return "Quarto Standard Nº" + getNumero() +
               " - Preço: R$" + calcularPreco() +
               " - Disponível: " + estaDisponivel();
    }

    public String getTipo() {
        return tipo;
    }
}