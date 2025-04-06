package com.hotel_transylvania.entities;

import java.util.List;

public class QuartoSuite extends Quarto {

    private String tipo;
    private List<String> servicosExtras;

    public QuartoSuite(int numero, double preco, List<String> servicosExtras) {
        super(numero, preco);
        this.tipo = "Suite";
        this.servicosExtras = servicosExtras;
    }

    @Override
    public double calcularPreco() {
        // preco temporário para os servicos extras.
    	//deve ser mudado ***********
        double adicional = servicosExtras.size() * 50.0;
        return getPreco() + adicional;
    }

    @Override
    public String exibirInfo() {
        return "Quarto Suíte Nº" + getNumero() +
               " - Preço com adicionais: R$" + calcularPreco() +
               " - Serviços extras: " + servicosExtras +
               " - Disponível: " + estaDisponivel();
    }

    public String getTipo() {
        return tipo;
    }

    public List<String> getServicosExtras() {
        return servicosExtras;
    }

    public void setServicosExtras(List<String> servicosExtras) {
        this.servicosExtras = servicosExtras;
    }
}