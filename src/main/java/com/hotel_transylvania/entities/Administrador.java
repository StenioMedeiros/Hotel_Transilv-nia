package com.hotel_transylvania.entities;

import java.util.List;

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
    
    public String gerenciaReservas(List<Reserva> reservas, String acao) {
        StringBuilder resultado = new StringBuilder();
        reservas.forEach(reserva -> {
            if ("confirmar".equalsIgnoreCase(acao)) {
                reserva.confirmarReserva();
                resultado.append("Reserva ").append(reserva.getId()).append(" confirmada\n");
            } else if ("cancelar".equalsIgnoreCase(acao)) {
                reserva.cancelarReserva();
                resultado.append("Reserva ").append(reserva.getId()).append(" cancelada\n");
            }
        });
        return resultado.toString();
    }
    
    public List<Hospede> listarHospedes() {
        // Implementation would query the database
        return List.of();
    }
}

