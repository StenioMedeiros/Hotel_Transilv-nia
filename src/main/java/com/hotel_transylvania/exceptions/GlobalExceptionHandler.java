package com.hotel_transylvania.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({UsuarioJaExisteException.class, CpfInvalidoException.class})
    public ResponseEntity<String> handleBadRequestExceptions(HotelTransylvaniaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body("Ocorreu um erro inesperado: " + e.getMessage());     
    }
        
    @ExceptionHandler(QuartoNaoEncontradoException.class)
    public ResponseEntity<String> handleQuartoNaoEncontrado(QuartoNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({NumeroQuartoJaExisteException.class, TipoQuartoInvalidoException.class, 
                      PrecoQuartoInvalidoException.class})
    public ResponseEntity<String> handleQuartoBadRequests(HotelTransylvaniaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(QuartoNaoDisponivelException.class)
    public ResponseEntity<String> handleQuartoNaoDisponivel(QuartoNaoDisponivelException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }  
    
    @ExceptionHandler(ServicoExtraNaoEncontradoException.class)
    public ResponseEntity<String> handleServicoExtraNaoEncontrado(ServicoExtraNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ServicoExtraJaExisteException.class, PrecoServicoInvalidoException.class})
    public ResponseEntity<String> handleServicoExtraBadRequests(HotelTransylvaniaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(ReservaNaoEncontradaException.class)
    public ResponseEntity<String> handleReservaNaoEncontrada(ReservaNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({DataCheckInInvalidaException.class, DataCheckOutInvalidaException.class, 
                      DatasReservaObrigatoriasException.class})
    public ResponseEntity<String> handleDataReservaExceptions(HotelTransylvaniaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservaConflitanteException.class)
    public ResponseEntity<String> handleReservaConflitante(ReservaConflitanteException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({ReservaJaConfirmadaException.class, ReservaJaCanceladaException.class})
    public ResponseEntity<String> handleStatusReservaExceptions(HotelTransylvaniaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}