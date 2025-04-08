package com.hotel_transylvania.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel_transylvania.enums.StatusReserva;
import com.hotel_transylvania.enums.TipoReserva;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospede_id", nullable = false)
    @JsonIgnore
    private Hospede hospede;

    @ManyToOne
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @Column(name = "data_check_in", nullable = false)
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out", nullable = false)
    private LocalDate dataCheckOut;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoReserva tipo;

    // Constructors, getters, and setters
    public Reserva() {
        this.status = StatusReserva.PENDENTE;
    }

    public Reserva(Hospede hospede, Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut, TipoReserva tipo) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.tipo = tipo;
        this.status = StatusReserva.PENDENTE;
        this.valorTotal = calcularTotal();
    }

    public BigDecimal calcularTotal() {
        long dias = dataCheckIn.until(dataCheckOut).getDays();
        return quarto.calcularPrecoTotal().multiply(BigDecimal.valueOf(dias));
    }

    public void confirmarReserva() {
        if (this.status == StatusReserva.PENDENTE) {
            this.status = StatusReserva.CONFIRMADA;
            this.quarto.setDisponivel(false);
        }
    }

    public void cancelarReserva() {
        if (this.status != StatusReserva.CANCELADA) {
            this.status = StatusReserva.CANCELADA;
            this.quarto.setDisponivel(true);
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
        this.valorTotal = calcularTotal();
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
        this.valorTotal = calcularTotal();
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusReserva getStatus() {
        return status;
    }

    public TipoReserva getTipo() {
        return tipo;
    }

    public void setTipo(TipoReserva tipo) {
        this.tipo = tipo;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}
    
}
