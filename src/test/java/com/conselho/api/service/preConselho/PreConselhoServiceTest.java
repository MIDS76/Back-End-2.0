package com.conselho.api.service.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PreConselhoServiceTest {
    @InjectMocks
    private PreConselhoService service;

    @Mock
    private PreConselhoRepository preConselhoRepository;

    @Test
    void criarPreConselhoAutomatico() {
        LocalDate dataInicio = LocalDate.of(2025, 12, 1);
        LocalDate dataFim = LocalDate.of(2025, 12, 1);

        PreConselhoRequestDTO request = new PreConselhoRequestDTO(1L, dataInicio, dataFim);

        PreConselho preConselho = new PreConselho();
        PreConselho salvo = new PreConselho();

        PreConselhoResponseDTO response = new PreConselhoResponseDTO()
    }

    @Test
    void buscarTodos() {
    }

    @Test
    void buscarPorId() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}