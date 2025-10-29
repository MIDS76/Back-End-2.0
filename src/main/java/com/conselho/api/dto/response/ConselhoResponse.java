package com.conselho.api.dto.response;

import java.time.LocalDate;

public record ConselhoResponse(
        Long idTurma,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long idRepresentante1,
        Long idRepresentante2,
        Long idPedagogico,
        String etapas
) {
}
