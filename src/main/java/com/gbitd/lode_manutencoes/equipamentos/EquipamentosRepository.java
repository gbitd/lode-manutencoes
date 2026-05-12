package com.gbitd.lode_manutencoes.equipamentos;

import com.gbitd.lode_manutencoes.equipamentos.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamento, Long> {

    List<Equipamento> findByStatus(String status);

    // O JpaRepository já fornece os métodos findAll, findById, save e deleteById prontos.
}
