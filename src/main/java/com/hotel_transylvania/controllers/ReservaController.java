package com.hotel_transylvania.controllers;

import com.hotel_transylvania.dtos.ReservaDTO;
import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.exceptions.*;
import com.hotel_transylvania.services.ReservaService;

import jakarta.validation.Valid;

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
    public ResponseEntity<?> criarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
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
        } catch (ReservaNaoEncontradaException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (ReservaJaConfirmadaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.internalServerError().body("Erro ao confirmar reserva");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        try {
            reservaService.cancelarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (ReservaNaoEncontradaException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (ReservaJaCanceladaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.internalServerError().body("Erro ao cancelar reserva");
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

