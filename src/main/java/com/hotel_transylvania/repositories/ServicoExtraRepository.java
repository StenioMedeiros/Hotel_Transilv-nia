package com.hotel_transylvania.repositories;


import com.hotel_transylvania.entities.ServicoExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoExtraRepository extends JpaRepository<ServicoExtra, Long> {
    List<ServicoExtra> findByQuartoId(Long quartoId);
    boolean existsByNomeAndQuartoId(String nome, Long quartoId);
}