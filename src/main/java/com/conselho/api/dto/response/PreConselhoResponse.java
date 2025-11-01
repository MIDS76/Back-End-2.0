package com.conselho.api.dto.response;

import java.time.LocalDate;

public record PreConselhoResponse(
        Long id,
        Long idConselho,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
