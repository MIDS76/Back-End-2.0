package com.conselho.api.dto.response;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public record TurmaResponseDTO(
        Long id,
        String nome,
        String curso,
        LocalDate dataInicio,
        LocalDate dataFinal
){
}
