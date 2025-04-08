package com.hotel_transylvania.services;

import com.hotel_transylvania.dtos.ReservaDTO;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.enums.StatusReserva;
import com.hotel_transylvania.exceptions.*;
import com.hotel_transylvania.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioServices hospedeService;

    @Autowired
    private QuartoService quartoService;

    public Reserva criarReserva(ReservaDTO reservaDTO) throws HotelTransylvaniaException {
        // Validações básicas
        if (reservaDTO.getDataCheckIn() == null || reservaDTO.getDataCheckOut() == null) {
            throw new DatasReservaObrigatoriasException();
        }

        Hospede hospede = hospedeService.buscarHospedePorId(reservaDTO.getHospedeId());
        Quarto quarto = quartoService.buscarPorId(reservaDTO.getQuartoId())
                .orElseThrow(() ->  new QuartoNaoEncontradoException());

        if (!quarto.getDisponivel()) {
            throw new QuartoNaoDisponivelException();
        }

        if (reservaDTO.getDataCheckIn().isBefore(LocalDate.now())) {
            throw new DataCheckInInvalidaException();
        }

        if (reservaDTO.getDataCheckOut().isBefore(reservaDTO.getDataCheckIn())) {
            throw new DataCheckOutInvalidaException();
        }

        if (reservaRepository.existsReservaConflitante(quarto.getId(), reservaDTO.getDataCheckIn(), reservaDTO.getDataCheckOut())) {
            throw new ReservaConflitanteException();
        }

        Reserva reserva = new Reserva(
                hospede,
                quarto,
                reservaDTO.getDataCheckIn(),
                reservaDTO.getDataCheckOut(),
                reservaDTO.getTipo()
        );

        hospede.adicionarReserva(reserva);
        return reservaRepository.save(reserva);
    }

    public Reserva confirmarReserva(Long id) throws HotelTransylvaniaException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNaoEncontradaException());
        
        if (reserva.getStatus() == StatusReserva.CONFIRMADA) {
            throw new ReservaJaConfirmadaException();
        }
        
        reserva.confirmarReserva();
        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Long id) throws HotelTransylvaniaException {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNaoEncontradaException());
        
        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new ReservaJaCanceladaException();
        }
        
        reserva.cancelarReserva();
        reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservasPorHospede(Long hospedeId) {
        return reservaRepository.findByHospedeId(hospedeId);
    }

    public List<Reserva> listarTodasReservas() {
        return reservaRepository.findAll();
    }
}