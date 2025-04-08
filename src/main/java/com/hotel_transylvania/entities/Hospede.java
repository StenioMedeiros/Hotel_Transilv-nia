package com.hotel_transylvania.entities;

import java.util.ArrayList;
import java.util.List;

import com.hotel_transylvania.enums.TipoUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Hospede extends Usuario {
	
    @OneToMany(mappedBy = "hospede", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Hospede() {
        super();
    }

    public Hospede(String nome, String cpf, String telefone, String email) {
        super(nome, cpf, telefone, email, TipoUsuario.HOSPEDE);
    }
    
    public void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setHospede(this);
    }

    public void cancelarReserva(Long reservaId) {
        reservas.stream()
                .filter(r -> r.getId().equals(reservaId))
                .findFirst()
                .ifPresent(Reserva::cancelarReserva);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public String dizerReserva() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservas do hóspede ").append(getNome()).append(":\n");
        reservas.forEach(reserva -> 
            sb.append("- Quarto #").append(reserva.getQuarto().getNumero())
             .append(", Status: ").append(reserva.getStatus())
             .append(", Período: ").append(reserva.getDataCheckIn())
             .append(" a ").append(reserva.getDataCheckOut())
             .append("\n"));
        return sb.toString();
    }

    public List<String> visualizarEventos() {
        List<String> eventos = new ArrayList<>();
        reservas.forEach(reserva -> 
            eventos.add(String.format("Reserva %d: Quarto %d de %s até %s",
                reserva.getId(),
                reserva.getQuarto().getNumero(),
                reserva.getDataCheckIn(),
                reserva.getDataCheckOut())));
        return eventos;
    }
}

