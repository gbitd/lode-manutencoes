package com.gbitd.lode_manutencoes.equipamentos;

import com.gbitd.lode_manutencoes.equipamentos.Equipamento;
import com.gbitd.lode_manutencoes.equipamentos.EquipamentosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    private final EquipamentosRepository repository;

    public EquipamentosController(EquipamentosRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Equipamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipamento criar(@RequestBody Equipamento equipamento) {
        return repository.save(equipamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(@PathVariable Long id, @RequestBody Equipamento dados) {
        return repository.findById(id).map(equipamento -> {
            equipamento.setNome(dados.getNome());
            equipamento.setTipo(dados.getTipo());
            equipamento.setDataInstalacao(dados.getDataInstalacao());
            equipamento.setStatus(dados.getStatus());
            return ResponseEntity.ok(repository.save(equipamento));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/manutencao")
    public ResponseEntity<Equipamento> registrarManutencao(@PathVariable Long id) {
        return repository.findById(id).map(equipamento -> {
            equipamento.setStatus("Manutenção Necessária");
            return ResponseEntity.ok(repository.save(equipamento));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
