package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoUsuario;

import jakarta.persistence.Entity;

@Entity
public class Hospede extends Usuario {

    public Hospede() {
        super();
    }

    public Hospede(String nome, String cpf, String telefone, String email) {
        super(nome, cpf, telefone, email, TipoUsuario.HOSPEDE);
    }
}

