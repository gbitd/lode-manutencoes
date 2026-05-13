package com.gbitd.lode_manutencoes.equipamentos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipamentosServiceTest {

    @Mock
    private EquipamentosRepository repository;

    @InjectMocks
    private EquipamentosService service;

    private Equipamento equipamento;

    @BeforeEach
    void setUp() {
        equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setNome("Bomba 04");
        equipamento.setTipo("Bomba");
        equipamento.setStatus("OPERACIONAL");
    }

    @Test
    void deveLancarExcecaoAoCriarEquipamentoSemNome() {
        Equipamento semNome = new Equipamento();
        semNome.setNome("");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.criar(semNome)
        );

        assertEquals("Nome é obrigatório", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveAlterarStatusComValorValido() {
        when(repository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(repository.save(equipamento)).thenReturn(equipamento);

        Equipamento resultado = service.atualizarStatus(1L, "CRITICO");

        assertEquals("CRITICO", resultado.getStatus());
        verify(repository).save(equipamento);
    }

    @Test
    void deveLancarExcecaoAoAlterarStatusInvalido() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.atualizarStatus(1L, "blablablaBLA")
        );

        assertTrue(ex.getMessage().contains("Status inválido"));
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }
}
