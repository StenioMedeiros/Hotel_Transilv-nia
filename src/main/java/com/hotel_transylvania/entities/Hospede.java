package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoUsuario;
import com.hotel_transylvania.model.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
public class Hospede extends Usuario {
    public Hospede(String nome, String cpf, String telefone, String email) {
        super(null, nome, cpf, telefone, email, TipoUsuario.HOSPEDE);
    }
}
