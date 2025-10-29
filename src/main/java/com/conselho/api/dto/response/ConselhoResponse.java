package com.conselho.api.dto.response;

import java.time.LocalDate;

public record ConselhoResponse(
        Long id,
        Long idTurma,
        String nomeTurma,
        Long idRepresentante1,
        String nomeRepresentante1,
        Long idRepresentante2,
        String nomeRepresentante2,
        Long idPedagogico,
        String nomePedagogico,
        LocalDate dataInicio,
        LocalDate dataFim,
        String etapas
) {
}
