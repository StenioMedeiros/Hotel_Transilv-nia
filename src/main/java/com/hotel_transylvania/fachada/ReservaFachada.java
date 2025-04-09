package com.hotel_transylvania.fachada;

import com.hotel_transylvania.dtos.ReservaDTO;
import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.exceptions.HotelTransylvaniaException;
import com.hotel_transylvania.services.ReservaService;

import java.util.List;

public class ReservaFachada {

    private ReservaService reservaService;

    public ReservaFachada(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    public Reserva criarReserva(ReservaDTO reservaDTO) throws HotelTransylvaniaException {
        return reservaService.criarReserva(reservaDTO);
    }


    public Reserva confirmarReserva(Long id) throws HotelTransylvaniaException {
        return reservaService.confirmarReserva(id);
    }

 
    public void cancelarReserva(Long id) throws HotelTransylvaniaException {
        reservaService.cancelarReserva(id);
    }

 
    public List<Reserva> listarReservasPorHospede(Long hospedeId) {
        return reservaService.listarReservasPorHospede(hospedeId);
    }

    public List<Reserva> listarTodasReservas() {
        return reservaService.listarTodasReservas();
    }
}
