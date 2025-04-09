package com.hoteltransylvania.hotel_transylvania;

import com.hotel_transylvania.dtos.AdministradorDTO;
import com.hotel_transylvania.dtos.HospedeDTO;
import com.hotel_transylvania.entities.Administrador;
import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.enums.TipoUsuario;
import com.hotel_transylvania.exceptions.CpfInvalidoException;
import com.hotel_transylvania.exceptions.CpfDuplicadoException;
import com.hotel_transylvania.exceptions.UsuarioNaoEncontradoException;
import com.hotel_transylvania.repositories.AdministradorRepository;
import com.hotel_transylvania.repositories.HospedeRepository;
import com.hotel_transylvania.services.UsuarioServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UsuarioServicesTest {

    @Mock
    private HospedeRepository hospedeRepository;

    @Mock
    private AdministradorRepository administradorRepository;

    @InjectMocks
    private UsuarioServices usuarioServices;

    private Hospede hospede;
    private Administrador administrador;
    private final String CPF_VALIDO = "12345678901";
    private final String CPF_INVALIDO = "123";

    @BeforeEach
    void setUp() {
        hospede = new Hospede("João Silva", CPF_VALIDO, "11999999999", "joao@email.com");
        hospede.setId(1L);
        
        administrador = new Administrador("Admin", CPF_VALIDO, "11988888888", "admin@email.com");
        administrador.setId(1L);
    }

    // Testes para cadastrarHospede
    @Test
    void cadastrarHospede_deveRetornarHospedeSalvo_quandoDadosValidos() {
        when(hospedeRepository.findByCpf(anyString())).thenReturn(null);
        when(hospedeRepository.save(any(Hospede.class))).thenReturn(hospede);

        Hospede resultado = usuarioServices.cadastrarHospede(createValidHospedeDTO());

        assertNotNull(resultado);
        assertEquals(hospede.getNome(), resultado.getNome());
        assertEquals(hospede.getCpf(), resultado.getCpf());
        verify(hospedeRepository, times(1)).save(any(Hospede.class));
    }

    @Test
    void cadastrarHospede_deveLancarExcecao_quandoCpfInvalido() {
        HospedeDTO dto = createValidHospedeDTO();
        dto.setCpf(CPF_INVALIDO);

        assertThrows(CpfInvalidoException.class, () -> usuarioServices.cadastrarHospede(dto));
        verify(hospedeRepository, never()).save(any(Hospede.class));
    }

    @Test
    void cadastrarHospede_deveLancarExcecao_quandoHospedeJaExiste() {
        when(hospedeRepository.findByCpf(anyString())).thenReturn(hospede);

        assertThrows(CpfDuplicadoException.class, () -> usuarioServices.cadastrarHospede(createValidHospedeDTO()));
        verify(hospedeRepository, never()).save(any(Hospede.class));
    }

    // Testes para cadastrarAdministrador
    @Test
    void cadastrarAdministrador_deveRetornarAdministradorSalvo_quandoDadosValidos() {
        when(administradorRepository.findByCpf(anyString())).thenReturn(null);
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administrador);

        Administrador resultado = usuarioServices.cadastrarAdministrador(createValidAdministradorDTO());

        assertNotNull(resultado);
        assertEquals(administrador.getNome(), resultado.getNome());
        assertEquals(administrador.getCpf(), resultado.getCpf());
        verify(administradorRepository, times(1)).save(any(Administrador.class));
    }

    @Test
    void cadastrarAdministrador_deveLancarExcecao_quandoCpfInvalido() {
        AdministradorDTO dto = createValidAdministradorDTO();
        dto.setCpf(CPF_INVALIDO);

        assertThrows(CpfInvalidoException.class, () -> usuarioServices.cadastrarAdministrador(dto));
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    @Test
    void cadastrarAdministrador_deveLancarExcecao_quandoAdministradorJaExiste() {
        when(administradorRepository.findByCpf(anyString())).thenReturn(administrador);

        assertThrows(CpfDuplicadoException.class, () -> usuarioServices.cadastrarAdministrador(createValidAdministradorDTO()));
        verify(administradorRepository, never()).save(any(Administrador.class));
    }

    // Testes para listarHospedes
    @Test
    void listarHospedes_deveRetornarListaHospedes_quandoHospedesExistem() {
        when(hospedeRepository.findByTipoUsuario(TipoUsuario.HOSPEDE)).thenReturn(Arrays.asList(hospede));

        List<Hospede> resultado = usuarioServices.listarHospedes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(hospede.getNome(), resultado.get(0).getNome());
    }

    @Test
    void listarHospedes_deveLancarExcecao_quandoNenhumHospedeEncontrado() {
        when(hospedeRepository.findByTipoUsuario(TipoUsuario.HOSPEDE)).thenReturn(Collections.emptyList());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.listarHospedes());
    }

    // Testes para listarAdministradores
    @Test
    void listarAdministradores_deveRetornarListaAdministradores_quandoAdministradoresExistem() {
        when(administradorRepository.findByTipoUsuario(TipoUsuario.ADMINISTRADOR)).thenReturn(Arrays.asList(administrador));

        List<Administrador> resultado = usuarioServices.listarAdministradores();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(administrador.getNome(), resultado.get(0).getNome());
    }

    @Test
    void listarAdministradores_deveLancarExcecao_quandoNenhumAdministradorEncontrado() {
        when(administradorRepository.findByTipoUsuario(TipoUsuario.ADMINISTRADOR)).thenReturn(Collections.emptyList());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.listarAdministradores());
    }

    // Testes para buscarHospedePorId
    @Test
    void buscarHospedePorId_deveRetornarHospede_quandoIdExistente() {
        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));

        Hospede resultado = usuarioServices.buscarHospedePorId(1L);

        assertNotNull(resultado);
        assertEquals(hospede.getId(), resultado.getId());
    }

    @Test
    void buscarHospedePorId_deveLancarExcecao_quandoIdInexistente() {
        when(hospedeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarHospedePorId(1L));
    }

    // Testes para buscarAdministradorPorId
    @Test
    void buscarAdministradorPorId_deveRetornarAdministrador_quandoIdExistente() {
        when(administradorRepository.findById(1L)).thenReturn(Optional.of(administrador));

        Administrador resultado = usuarioServices.buscarAdministradorPorId(1L);

        assertNotNull(resultado);
        assertEquals(administrador.getId(), resultado.getId());
    }

    @Test
    void buscarAdministradorPorId_deveLancarExcecao_quandoIdInexistente() {
        when(administradorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarAdministradorPorId(1L));
    }

    // Testes para buscarHospedePorCpf
    @Test
    void buscarHospedePorCpf_deveRetornarHospede_quandoCpfExistente() {
        when(hospedeRepository.findByCpf(CPF_VALIDO)).thenReturn(hospede);

        Hospede resultado = usuarioServices.buscarHospedePorCpf(CPF_VALIDO);

        assertNotNull(resultado);
        assertEquals(hospede.getCpf(), resultado.getCpf());
    }

    @Test
    void buscarHospedePorCpf_deveLancarExcecao_quandoCpfInvalido() {
        assertThrows(CpfInvalidoException.class, () -> usuarioServices.buscarHospedePorCpf(CPF_INVALIDO));
        verify(hospedeRepository, never()).findByCpf(anyString());
    }

    @Test
    void buscarHospedePorCpf_deveLancarExcecao_quandoCpfInexistente() {
        when(hospedeRepository.findByCpf(anyString())).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarHospedePorCpf(CPF_VALIDO));
    }

    // Testes para buscarAdministradorPorCpf
    @Test
    void buscarAdministradorPorCpf_deveRetornarAdministrador_quandoCpfExistente() {
        when(administradorRepository.findByCpf(CPF_VALIDO)).thenReturn(administrador);

        Administrador resultado = usuarioServices.buscarAdministradorPorCpf(CPF_VALIDO);

        assertNotNull(resultado);
        assertEquals(administrador.getCpf(), resultado.getCpf());
    }

    @Test
    void buscarAdministradorPorCpf_deveLancarExcecao_quandoCpfInvalido() {
        assertThrows(CpfInvalidoException.class, () -> usuarioServices.buscarAdministradorPorCpf(CPF_INVALIDO));
        verify(administradorRepository, never()).findByCpf(anyString());
    }

    @Test
    void buscarAdministradorPorCpf_deveLancarExcecao_quandoCpfInexistente() {
        when(administradorRepository.findByCpf(anyString())).thenReturn(null);

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarAdministradorPorCpf(CPF_VALIDO));
    }

    // Testes para buscarHospedePorNome
    @Test
    void buscarHospedePorNome_deveRetornarListaHospedes_quandoNomeExistente() {
        when(hospedeRepository.findByNomeContainingIgnoreCase("João")).thenReturn(Arrays.asList(hospede));

        List<Hospede> resultado = usuarioServices.buscarHospedePorNome("João");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getNome().contains("João"));
    }

    @Test
    void buscarHospedePorNome_deveLancarExcecao_quandoNenhumHospedeEncontrado() {
        when(hospedeRepository.findByNomeContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarHospedePorNome("Inexistente"));
    }

    // Testes para buscarAdministradorPorNome
    @Test
    void buscarAdministradorPorNome_deveRetornarListaAdministradores_quandoNomeExistente() {
        when(administradorRepository.findByNomeContainingIgnoreCase("Admin")).thenReturn(Arrays.asList(administrador));

        List<Administrador> resultado = usuarioServices.buscarAdministradorPorNome("Admin");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getNome().contains("Admin"));
    }

    @Test
    void buscarAdministradorPorNome_deveLancarExcecao_quandoNenhumAdministradorEncontrado() {
        when(administradorRepository.findByNomeContainingIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.buscarAdministradorPorNome("Inexistente"));
    }

    // Testes para checkOutHospede
    @Test
    void checkOutHospede_deveDeletarHospede_quandoIdExistente() {
        when(hospedeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(hospedeRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioServices.checkOutHospede(1L));
        verify(hospedeRepository, times(1)).deleteById(1L);
    }

    @Test
    void checkOutHospede_deveLancarExcecao_quandoIdInexistente() {
        when(hospedeRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioServices.checkOutHospede(1L));
        verify(hospedeRepository, never()).deleteById(anyLong());
    }

    // Métodos auxiliares
    private HospedeDTO createValidHospedeDTO() {
        HospedeDTO dto = new HospedeDTO();
        dto.setNome("João Silva");
        dto.setCpf(CPF_VALIDO);
        dto.setTelefone("11999999999");
        dto.setEmail("joao@email.com");
        dto.setTipoUsuario(TipoUsuario.HOSPEDE);
        return dto;
    }

    private AdministradorDTO createValidAdministradorDTO() {
        AdministradorDTO dto = new AdministradorDTO();
        dto.setNome("Admin");
        dto.setCpf(CPF_VALIDO);
        dto.setTelefone("11988888888");
        dto.setEmail("admin@email.com");
        dto.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
        return dto;
    }
}