package com.gbitd.lode_manutencoes.equipamentos;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipamentosService {

    private static final List<String> STATUS_VALIDOS = List.of(
        "OPERACIONAL",
        "MANUTENCAO_NECESSARIA",
        "CRITICO"
    );

    private final EquipamentosRepository repository;

    public EquipamentosService(EquipamentosRepository repository) {
        this.repository = repository;
    }

    public List<Equipamento> listar() {
        return repository.findAll();
    }

    public Equipamento criar(Equipamento equipamento) {
        if (equipamento.getNome() == null || equipamento.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        return repository.save(equipamento);
    }

    public Equipamento atualizarStatus(Long id, String status) {
        if (!STATUS_VALIDOS.contains(status)) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
        Equipamento equipamento = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
        equipamento.setStatus(status);
        return repository.save(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento dados) {
        Equipamento equipamento = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
        equipamento.setNome(dados.getNome());
        equipamento.setTipo(dados.getTipo());
        equipamento.setDataInstalacao(dados.getDataInstalacao());
        equipamento.setStatus(dados.getStatus());
        return repository.save(equipamento);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Equipamento não encontrado");
        }
        repository.deleteById(id);
    }
}
