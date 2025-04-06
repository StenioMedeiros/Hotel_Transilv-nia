package com.hotel_transylvania.entities;

public abstract class Quarto {
	
	private int numero;
	private double preco;
	private boolean disponivel;
	
	public Quarto(int numero, double preco) { //Construtor
	    this.numero = numero;
	    this.preco = preco;
	    this.disponivel = true;
	}

//Métodos:

	public void reservar() {
		this.disponivel = false;
	}
	
	public void liberar() {
		this.disponivel = true;
	}
	
	public boolean estaDisponivel() {
		return this.disponivel;
	}
	
	public abstract double calcularPreco();
	public abstract String exibirInfo();
	
	//getters e setters:
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public int getNumero() {
		return numero;
	}

}
