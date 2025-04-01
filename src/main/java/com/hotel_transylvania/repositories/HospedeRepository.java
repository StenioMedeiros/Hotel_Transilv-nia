package com.hotel_transylvania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel_transylvania.entities.Hospede;
import com.hotel_transylvania.enums.TipoUsuario;
import java.util.List;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long>{
    List<Hospede> findByTipoUsuario(TipoUsuario tipo);
    List<Hospede> findByNomeContainingIgnoreCase(String nome);
    Hospede findByCpf(String cpf);

}
