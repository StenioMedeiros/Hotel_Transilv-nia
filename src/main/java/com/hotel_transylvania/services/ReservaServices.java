package com.hotel_transylvania.services;

import com.hotel_transylvania.entities.Quarto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaServices {

    private List<Quarto> quartos = new ArrayList<>();

    public void adicionarQuarto(Quarto quarto) {
        quartos.add(quarto);
    }

    public boolean reservarQuarto(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero && q.estaDisponivel()) {
                q.reservar();
                return true;
            }
        }
        return false;
    }

    public boolean liberarQuarto(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero && !q.estaDisponivel()) {
                q.liberar();
                return true;
            }
        }
        return false;
    }

    public Quarto buscarPorNumero(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) return q;
        }
        return null;
    }

    public List<Quarto> listarQuartosDisponiveis() {
        List<Quarto> disponiveis = new ArrayList<>();
        for (Quarto q : quartos) {
            if (q.estaDisponivel()) disponiveis.add(q);
        }
        return disponiveis;
    }
}
