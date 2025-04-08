package com.hotel_transylvania.services;

import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.entities.Reserva;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class TemporadaService {

    public BigDecimal calcularDesconto(Reserva reserva) {
        LocalDate checkIn = reserva.getDataCheckIn();
        Month month = checkIn.getMonth();
        
        // High season - no discount
        if (List.of(Month.JANUARY, Month.FEBRUARY, Month.JULY, Month.DECEMBER).contains(month)) {
            return BigDecimal.ZERO;
        }
        // Medium season - 10% discount
        else if (List.of(Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE).contains(month)) {
            return reserva.getValorTotal().multiply(BigDecimal.valueOf(0.1));
        }
        // Low season - 20% discount
        else {
            return reserva.getValorTotal().multiply(BigDecimal.valueOf(0.2));
        }
    }

    public BigDecimal aplicarDescontoTemporada(Reserva reserva) {
        BigDecimal desconto = calcularDesconto(reserva);
        BigDecimal novoTotal = reserva.getValorTotal().subtract(desconto);
        reserva.setValorTotal(novoTotal);
        return desconto;
    }
}