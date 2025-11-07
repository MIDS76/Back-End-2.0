package com.conselho.api.dto.response;

public record ConselhoAlunoResponse(

        Long id,
        Long idAluno,
        String nomeAluno,
        Long idConselho
) {
}
