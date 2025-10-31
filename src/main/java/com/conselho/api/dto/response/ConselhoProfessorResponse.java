package com.conselho.api.dto.response;

public record ConselhoProfessorResponse (
        Long id,
        Long idConselho,
        Long idProfessor,
        String nomeProfessor
) {

}
