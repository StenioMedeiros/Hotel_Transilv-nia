package com.hotel_transylvania.repositories;

import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.enums.TipoQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    Quarto findByNumero(Integer numero);
    List<Quarto> findByDisponivel(Boolean disponivel);
    List<Quarto> findByTipo(TipoQuarto tipo);
    boolean existsByNumero(Integer numero);
}