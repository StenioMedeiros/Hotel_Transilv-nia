package com.hoteltransylvania.hotel_transylvania;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hotel_transylvania.App.HotelTransylvaniaApplication;
import com.hotel_transylvania.dtos.HospedeDTO;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.exceptions.CpfInvalidoException;
import com.hotel_transylvania.exceptions.UsuarioJaExisteException;
import com.hotel_transylvania.services.UsuarioServices;

@SpringBootTest(classes = HotelTransylvaniaApplication.class)
@Transactional 
public class HospedeTests {

    @Autowired
    private UsuarioServices usuarioServices;


    @Test
    void testCriarHospedeComSucesso() {
        HospedeDTO hospedeDto = new HospedeDTO();
        hospedeDto.setNome("João da Silva");
        hospedeDto.setCpf("12345678901");
        hospedeDto.setTelefone("11999990000");
        hospedeDto.setEmail("joao@example.com");

        Hospede hospedeCriado = usuarioServices.cadastrarHospede(hospedeDto);
        assertNotNull(hospedeCriado.getId(), "O ID do hospede não deve ser nulo após a criação");
        assertEquals("João da Silva", hospedeCriado.getNome());
        assertEquals("12345678901", hospedeCriado.getCpf());
    }


    @Test
    void testCriarHospedeCpfInvalido() {
        HospedeDTO hospedeDto = new HospedeDTO();
        hospedeDto.setNome("Maria Souza");
        hospedeDto.setCpf("123"); // CPF com formato inválido
        hospedeDto.setTelefone("11999991111");
        hospedeDto.setEmail("maria@example.com");

        assertThrows(CpfInvalidoException.class, () -> {
            usuarioServices.cadastrarHospede(hospedeDto);
        });
    }

  
    @Test
    void testCriarHospedeUsuarioJaExiste() {
        HospedeDTO hospedeDto = new HospedeDTO();
        hospedeDto.setNome("Pedro Oliveira");
        hospedeDto.setCpf("98765432100");
        hospedeDto.setTelefone("11999992222");
        hospedeDto.setEmail("pedro@example.com");

        Hospede hospedeCriado = usuarioServices.cadastrarHospede(hospedeDto);
        assertNotNull(hospedeCriado.getId());

        assertThrows(UsuarioJaExisteException.class, () -> {
            usuarioServices.cadastrarHospede(hospedeDto);
        });
    }
}
