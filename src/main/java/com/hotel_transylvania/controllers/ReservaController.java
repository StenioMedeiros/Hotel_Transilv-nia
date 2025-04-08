package com.hotel_transylvania.controllers;

import com.hotel_transylvania.dtos.ReservaDTO;
import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.exceptions.HotelTransylvaniaException;
import com.hotel_transylvania.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            Reserva reserva = reservaService.criarReserva(reservaDTO);
            return ResponseEntity.ok(reserva);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarReserva(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.confirmarReserva(id);
            return ResponseEntity.ok(reserva);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/hospede/{hospedeId}")
    public ResponseEntity<List<Reserva>> listarReservasPorHospede(@PathVariable Long hospedeId) {
        return ResponseEntity.ok(reservaService.listarReservasPorHospede(hospedeId));
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarTodasReservas() {
        return ResponseEntity.ok(reservaService.listarTodasReservas());
    }
}