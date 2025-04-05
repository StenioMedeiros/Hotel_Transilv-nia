package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoUsuario;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nome, String cpf, String telefone, String email) {
        super(nome, cpf, telefone, email, TipoUsuario.ADMINISTRADOR);
    }
}

