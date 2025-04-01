package com.hotel_transylvania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_transylvania.entities.Administrador;
import com.hotel_transylvania.enums.TipoUsuario;
import java.util.List;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByTipoUsuario(TipoUsuario tipo);
    List<Administrador> findByNomeContainingIgnoreCase(String nome);
    Administrador findByCpf(String cpf);

}
