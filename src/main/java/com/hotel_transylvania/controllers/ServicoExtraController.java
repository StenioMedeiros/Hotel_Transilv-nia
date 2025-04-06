package com.hotel_transylvania.controllers;

import com.hotel_transylvania.dtos.ServicoExtraDTO;
import com.hotel_transylvania.entities.ServicoExtra;
import com.hotel_transylvania.services.ServicoExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos-extras")
public class ServicoExtraController {

    @Autowired
    private ServicoExtraService servicoExtraService;

    @PostMapping
    public ResponseEntity<ServicoExtra> criarServicoExtra(@RequestBody ServicoExtraDTO servicoExtraDTO) {
        return ResponseEntity.ok(servicoExtraService.criarServicoExtra(servicoExtraDTO));
    }

    @GetMapping("/quarto/{quartoId}")
    public ResponseEntity<List<ServicoExtra>> listarPorQuarto(@PathVariable Long quartoId) {
        return ResponseEntity.ok(servicoExtraService.listarPorQuarto(quartoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoExtra> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicoExtraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoExtra> atualizarServicoExtra(
            @PathVariable Long id,
            @RequestBody ServicoExtraDTO servicoExtraDTO) {
        return ResponseEntity.ok(servicoExtraService.atualizarServicoExtra(id, servicoExtraDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServicoExtra(@PathVariable Long id) {
        servicoExtraService.deletarServicoExtra(id);
        return ResponseEntity.noContent().build();
    }
}