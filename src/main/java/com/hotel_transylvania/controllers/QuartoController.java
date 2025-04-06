package com.hotel_transylvania.controllers;

import com.hotel_transylvania.dtos.QuartoDTO;
import com.hotel_transylvania.entities.Quarto;
import com.hotel_transylvania.enums.TipoQuarto;
import com.hotel_transylvania.exceptions.QuartoNaoEncontradoException;
import com.hotel_transylvania.services.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @PostMapping("/cadastro")
    public ResponseEntity<Quarto> criarQuarto(@RequestBody QuartoDTO quartoDTO) {
        Quarto quarto = quartoService.criarQuarto(quartoDTO);
        return ResponseEntity.ok(quarto);
    }

    @GetMapping("/listaQuarto")
    public ResponseEntity<List<Quarto>> listarTodos() {
        return ResponseEntity.ok(quartoService.listarTodos());
    }

    @GetMapping("/disponiveis/{disponivel}")
    public ResponseEntity<List<Quarto>> listarPorDisponibilidade(@PathVariable Boolean disponivel) {
        return ResponseEntity.ok(quartoService.listarPorDisponibilidade(disponivel));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Quarto>> listarPorTipo(@PathVariable TipoQuarto tipo) {
        return ResponseEntity.ok(quartoService.listarPorTipo(tipo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quartoService.buscarPorId(id)
                .orElseThrow(() -> new QuartoNaoEncontradoException(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quarto> atualizarQuarto(@PathVariable Long id, @RequestBody QuartoDTO quartoDTO) {
        return ResponseEntity.ok(quartoService.atualizarQuarto(id, quartoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable Long id) {
        quartoService.deletarQuarto(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Quarto> alterarDisponibilidade(
            @PathVariable Long id,
            @RequestParam Boolean disponivel) {
        return ResponseEntity.ok(quartoService.alterarDisponibilidade(id, disponivel));
    }
    
    @GetMapping("/{id}/disponivel")
    public ResponseEntity<Void> verificarDisponibilidade(@PathVariable Long id) {
        quartoService.verificarDisponibilidade(id);
        return ResponseEntity.ok().build();
    }
}