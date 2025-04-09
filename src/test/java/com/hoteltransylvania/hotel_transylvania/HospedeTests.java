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
@Transactional // Garante rollback após cada teste
public class HospedeTests {

    @Autowired
    private UsuarioServices usuarioServices;

    /**
     * Testa a criação bem-sucedida de um Hospede com dados válidos.
     */
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

    /**
     * Testa se o cadastro lança exceção ao informar um CPF inválido.
     */
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

    /**
     * Testa se o cadastro lança exceção ao tentar criar um Hospede com um CPF já cadastrado.
     */
    @Test
    void testCriarHospedeUsuarioJaExiste() {
        HospedeDTO hospedeDto = new HospedeDTO();
        hospedeDto.setNome("Pedro Oliveira");
        hospedeDto.setCpf("98765432100");
        hospedeDto.setTelefone("11999992222");
        hospedeDto.setEmail("pedro@example.com");

        // Primeiro cadastro deve ser realizado com sucesso.
        Hospede hospedeCriado = usuarioServices.cadastrarHospede(hospedeDto);
        assertNotNull(hospedeCriado.getId());

        // Segunda tentativa com o mesmo CPF deverá lançar exceção.
        assertThrows(UsuarioJaExisteException.class, () -> {
            usuarioServices.cadastrarHospede(hospedeDto);
        });
    }
}
