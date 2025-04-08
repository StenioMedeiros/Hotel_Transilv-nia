package com.hotel_transylvania.repositories;

import com.hotel_transylvania.entities.Reserva;
import com.hotel_transylvania.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Busca todas as reservas de um hóspede específico
    List<Reserva> findByHospedeId(Long hospedeId);

    // Busca reservas por status
    List<Reserva> findByStatus(StatusReserva status);

    // Busca reservas por período
    @Query("SELECT r FROM Reserva r WHERE r.dataCheckIn BETWEEN :inicio AND :fim OR r.dataCheckOut BETWEEN :inicio AND :fim")
    List<Reserva> findReservasNoPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Verifica se existe reserva conflitante para um quarto
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Reserva r WHERE r.quarto.id = :quartoId " +
           "AND r.status <> com.hotel_transylvania.enums.StatusReserva.CANCELADA " +
           "AND ((r.dataCheckIn <= :dataCheckOut AND r.dataCheckOut >= :dataCheckIn))")
    boolean existsReservaConflitante(
            @Param("quartoId") Long quartoId,
            @Param("dataCheckIn") LocalDate dataCheckIn,
            @Param("dataCheckOut") LocalDate dataCheckOut);

    // Busca reservas por quarto
    List<Reserva> findByQuartoId(Long quartoId);

    // Busca reservas confirmadas para um quarto específico
    List<Reserva> findByQuartoIdAndStatus(Long quartoId, StatusReserva status);

    // Busca reservas que estão ativas (não canceladas) em uma determinada data
    @Query("SELECT r FROM Reserva r WHERE r.status <> com.hotel_transylvania.enums.StatusReserva.CANCELADA " +
           "AND :data BETWEEN r.dataCheckIn AND r.dataCheckOut")
    List<Reserva> findReservasAtivasNaData(@Param("data") LocalDate data);

    // Busca reservas por tipo
    @Query("SELECT r FROM Reserva r WHERE r.tipo = :tipo")
    List<Reserva> findByTipo(@Param("tipo") String tipo);

    @Query("SELECT r FROM Reserva r WHERE r.dataCheckIn = :data")
    List<Reserva> findByDataCheckIn(@Param("data") LocalDate data);

    @Query("SELECT r FROM Reserva r WHERE r.dataCheckOut = :data")
    List<Reserva> findByDataCheckOut(@Param("data") LocalDate data);
}