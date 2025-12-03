package com.conselho.api.dto.response;

import java.util.List;

public record UcProfessorResponseDTO(
        Long id,
        Long idConselho,
        Long idProfessor,
        String nomeProfessor,
        List<String> nomeUnidadeCurricular
) {

}
