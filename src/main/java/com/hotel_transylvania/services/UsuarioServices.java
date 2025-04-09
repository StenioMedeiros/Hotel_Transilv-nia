package com.hotel_transylvania.services;

import com.hotel_transylvania.repositories.HospedeRepository;
import com.hotel_transylvania.repositories.AdministradorRepository;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.entities.Administrador;
import com.hotel_transylvania.dtos.HospedeDTO;
import com.hotel_transylvania.dtos.AdministradorDTO;
import com.hotel_transylvania.enums.TipoUsuario;
import com.hotel_transylvania.exceptions.CpfInvalidoException;
import com.hotel_transylvania.exceptions.CpfDuplicadoException;
import com.hotel_transylvania.exceptions.UsuarioNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServices {
    
    @Autowired 
    private HospedeRepository hospedeRepository;
    
    @Autowired 
    private AdministradorRepository administradorRepository;

    public Hospede cadastrarHospede(HospedeDTO hospedeDto) {
        validarCpf(hospedeDto.getCpf());
        
        if (hospedeRepository.findByCpf(hospedeDto.getCpf()) != null) {
            throw new CpfDuplicadoException(
                "Já existe um hóspede cadastrado com o CPF: " + hospedeDto.getCpf()
            );
        }

        Hospede hospede = new Hospede(
            hospedeDto.getNome(),
            hospedeDto.getCpf(),
            hospedeDto.getTelefone(),
            hospedeDto.getEmail()
        );
        
        return hospedeRepository.save(hospede);
    }

    public Administrador cadastrarAdministrador(AdministradorDTO administradorDto) {
        validarCpf(administradorDto.getCpf());
        
        if (administradorRepository.findByCpf(administradorDto.getCpf()) != null) {
            throw new CpfDuplicadoException(
                "Já existe um administrador cadastrado com o CPF: " + administradorDto.getCpf()
            );
        }

        Administrador administrador = new Administrador(
            administradorDto.getNome(),
            administradorDto.getCpf(),
            administradorDto.getTelefone(),
            administradorDto.getEmail()
        );
        
        return administradorRepository.save(administrador);
    }

    public List<Hospede> listarHospedes() {
        List<Hospede> hospedes = hospedeRepository.findByTipoUsuario(TipoUsuario.HOSPEDE);
        if (hospedes.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum hóspede encontrado");
        }
        return hospedes;
    }

    public List<Administrador> listarAdministradores() {
        List<Administrador> administradores = administradorRepository.findByTipoUsuario(TipoUsuario.ADMINISTRADOR);
        if (administradores.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum administrador encontrado");
        }
        return administradores;
    }
    
    public Hospede buscarHospedePorId(Long id) {
        return hospedeRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Hóspede não encontrado com ID: " + id));
    }

    public Administrador buscarAdministradorPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Administrador não encontrado com ID: " + id));
    }
    
    public Hospede buscarHospedePorCpf(String cpf) {
        validarCpf(cpf);
        Hospede hospede = hospedeRepository.findByCpf(cpf);
        if (hospede == null) {
            throw new UsuarioNaoEncontradoException("Hóspede não encontrado com CPF: " + cpf);
        }
        return hospede;
    }

    public List<Hospede> buscarHospedePorNome(String nome) {
        List<Hospede> hospedes = hospedeRepository.findByNomeContainingIgnoreCase(nome);
        if (hospedes.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum hóspede encontrado com nome contendo: " + nome);
        }
        return hospedes;
    }

    public Administrador buscarAdministradorPorCpf(String cpf) {
        validarCpf(cpf);
        Administrador administrador = administradorRepository.findByCpf(cpf);
        if (administrador == null) {
            throw new UsuarioNaoEncontradoException("Administrador não encontrado com CPF: " + cpf);
        }
        return administrador;
    }

    public List<Administrador> buscarAdministradorPorNome(String nome) {
        List<Administrador> administradores = administradorRepository.findByNomeContainingIgnoreCase(nome);
        if (administradores.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Nenhum administrador encontrado com nome contendo: " + nome);
        }
        return administradores;
    }

    public void checkOutHospede(Long id) {
        if (!hospedeRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Hóspede não encontrado com ID: " + id);
        }
        hospedeRepository.deleteById(id);
    }

    private void validarCpf(String cpf) {
        // Implementação básica de validação de CPF
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new CpfInvalidoException("CPF inválido: " + cpf);
        }
    }
}
