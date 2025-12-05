package com.conselho.api.dto.response;


import java.time.LocalDate;

public record TurmaResponseDTO(
        Long id,
        String nome,
        String curso,
        LocalDate dataInicio,
        LocalDate dataFinal,
        Long idUltimoConselho
){
}
