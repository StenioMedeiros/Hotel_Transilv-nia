package com.hotel_transylvania.dtos;

import com.hotel_transylvania.enums.TipoReserva;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservaDTO {
    @NotNull(message = "O ID do hóspede é obrigatório")
    private Long hospedeId;

    @NotNull(message = "O ID do quarto é obrigatório")
    private Long quartoId;

    @NotNull(message = "A data de check-in é obrigatória")
    private LocalDate dataCheckIn;

    @NotNull(message = "A data de check-out é obrigatória")
    private LocalDate dataCheckOut;

    @NotNull(message = "O tipo de reserva é obrigatório")
    private TipoReserva tipo;


    // Getters and setters
    public Long getHospedeId() {
        return hospedeId;
    }

    public void setHospedeId(Long hospedeId) {
        this.hospedeId = hospedeId;
    }

    public Long getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(Long quartoId) {
        this.quartoId = quartoId;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public TipoReserva getTipo() {
        return tipo;
    }

    public void setTipo(TipoReserva tipo) {
        this.tipo = tipo;
    }
    
}