package com.hotel_transylvania.controllers;

import com.hotel_transylvania.services.UsuarioServices;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.exceptions.HotelTransylvaniaException;
import com.hotel_transylvania.entities.Administrador;
import com.hotel_transylvania.dtos.AdministradorDTO;
import com.hotel_transylvania.dtos.HospedeDTO;
import com.hotel_transylvania.dtos.UsuarioDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired 
    private UsuarioServices service;


    @PostMapping("/hospedes")
    public ResponseEntity<?> cadastrarHospede(@RequestBody HospedeDTO hospedeDto) {
        try {
            Hospede hospede = service.cadastrarHospede(hospedeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(hospede);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/administradores")
    public ResponseEntity<?> cadastrarAdministrador(@RequestBody AdministradorDTO administradorDto) {
        try {
            Administrador administrador = service.cadastrarAdministrador(administradorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/hospedes")
    public ResponseEntity<?> listarHospedes() {
        try {
            List<Hospede> hospedes = service.listarHospedes();
            return ResponseEntity.ok(hospedes);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/administradores")
    public ResponseEntity<?> listarAdministradores() {
        try {
            List<Administrador> administradores = service.listarAdministradores();
            return ResponseEntity.ok(administradores);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/hospedes/{id}")
    public ResponseEntity<?> buscarHospedePorId(@PathVariable Long id) {
        try {
            Hospede hospede = service.buscarHospedePorId(id);
            return ResponseEntity.ok(hospede);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/administradores/{id}")
    public ResponseEntity<?> buscarAdministradorPorId(@PathVariable Long id) {
        try {
            Administrador administrador = service.buscarAdministradorPorId(id);
            return ResponseEntity.ok(administrador);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/hospedes/nome/{nome}")
    public ResponseEntity<?> buscarHospedePorNome(@PathVariable String nome) {
        try {
            List<Hospede> hospedes = service.buscarHospedePorNome(nome);
            return ResponseEntity.ok(hospedes);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/hospedes/cpf/{cpf}")
    public ResponseEntity<?> buscarHospedePorCpf(@PathVariable String cpf) {
        try {
            Hospede hospede = service.buscarHospedePorCpf(cpf);
            return ResponseEntity.ok(hospede);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/administradores/nome/{nome}")
    public ResponseEntity<?> buscarAdministradorPorNome(@PathVariable String nome) {
        try {
            List<Administrador> administradores = service.buscarAdministradorPorNome(nome);
            return ResponseEntity.ok(administradores);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/administradores/cpf/{cpf}")
    public ResponseEntity<?> buscarAdministradorPorCpf(@PathVariable String cpf) {
        try {
            Administrador administrador = service.buscarAdministradorPorCpf(cpf);
            return ResponseEntity.ok(administrador);
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/hospedes/{id}")
    public ResponseEntity<?> checkOutHospede(@PathVariable Long id) {
        try {
            service.checkOutHospede(id);
            return ResponseEntity.noContent().build();
        } catch (HotelTransylvaniaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Handler global de exceções (opcional - pode ser em uma classe separada)
    @ExceptionHandler(HotelTransylvaniaException.class)
    public ResponseEntity<String> handleHotelTransylvaniaException(HotelTransylvaniaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}