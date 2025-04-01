package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoUsuario;
import com.hotel_transylvania.model.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
public class Administrador extends Usuario{
    public Administrador(String nome, String cpf, String telefone, String email) {
        super(null, nome, cpf, telefone, email, TipoUsuario.ADMINISTRADOR);
    }
}
