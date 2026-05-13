package com.gbitd.lode_manutencoes.equipamentos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    private final EquipamentosService service;

    public EquipamentosController(EquipamentosService service) {
        this.service = service;
    }

    @GetMapping
    public List<Equipamento> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Equipamento equipamento) {
        try {
            return ResponseEntity.ok(service.criar(equipamento));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Equipamento dados) {
        try {
            return ResponseEntity.ok(service.atualizar(id, dados));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(service.atualizarStatus(id, status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
