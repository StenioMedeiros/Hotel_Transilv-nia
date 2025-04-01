package com.hotel_transylvania.controllers;

import com.hotel_transylvania.services.UsuarioServices;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.entities.Administrador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired 
    private UsuarioServices service;

    @PostMapping("/hospedes")
    public Hospede cadastrarHospede(@RequestBody Hospede hospede) {
        return service.cadastrarHospede(hospede);
    }

    @PostMapping("/administradores")
    public Administrador cadastrarAdministrador(@RequestBody Administrador administrador) {
        return service.cadastrarAdministrador(administrador);
    }

    @GetMapping("/hospedes")
    public List<Hospede> listarHospedes() {
        return service.listarHospedes();
    }

    @GetMapping("/administradores")
    public List<Administrador> listarAdministradores() {
        return service.listarAdministradores();
    }

    @GetMapping("/hospedes/nome/{nome}")
    public List<Hospede> buscarHospedePorNome(@PathVariable String nome) {
        return service.buscarHospedePorNome(nome);
    }

    @GetMapping("/hospedes/cpf/{cpf}")
    public Hospede buscarHospedePorCpf(@PathVariable String cpf) {
        return service.buscarHospedePorCpf(cpf);
    }

    @GetMapping("/administradores/nome/{nome}")
    public List<Administrador> buscarAdministradorPorNome(@PathVariable String nome) {
        return service.buscarAdministradorPorNome(nome);
    }

    @GetMapping("/administradores/cpf/{cpf}")
    public Administrador buscarAdministradorPorCpf(@PathVariable String cpf) {
        return service.buscarAdministradorPorCpf(cpf);
    }

    @DeleteMapping("/hospedes/{id}")
    public void checkOutHospede(@PathVariable Long id) {
        service.checkOutHospede(id);
    }
}

