package com.hotel_transylvania.services;

import com.hotel_transylvania.repositories.HospedeRepository;
import com.hotel_transylvania.repositories.AdministradorRepository;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.entities.Administrador;
import com.hotel_transylvania.enums.TipoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
public class UsuarioServices {
	
	@Autowired 
    private HospedeRepository hospedeRepository;
	@Autowired 
    private AdministradorRepository administradorRepository;


    public Hospede cadastrarHospede(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    public Administrador cadastrarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public List<Hospede> listarHospedes() {
        return hospedeRepository.findByTipoUsuario(TipoUsuario.HOSPEDE);
    }

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findByTipoUsuario(TipoUsuario.ADMINISTRADOR);
    }

    public Hospede buscarHospedePorCpf(String cpf) {
        return hospedeRepository.findByCpf(cpf);
    }

    public List<Hospede> buscarHospedePorNome(String nome) {
        return hospedeRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Administrador buscarAdministradorPorCpf(String cpf) {
        return administradorRepository.findByCpf(cpf);
    }

    public List<Administrador> buscarAdministradorPorNome(String nome) {
        return administradorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void checkOutHospede(Long id) {
        hospedeRepository.deleteById(id);
    }
}
