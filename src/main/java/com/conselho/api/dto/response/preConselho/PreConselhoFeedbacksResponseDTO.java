package com.conselho.api.dto.response.preConselho;

import java.util.List;

public record PreConselhoFeedbacksResponseDTO (
        Long id,
        Long idConselho,
        List<PreConselhoPedagogicoResponseDTO> preConselhoPedagogicos,
        List<PreConselhoProfessorResponseDTO> preConselhoProfessores,
        List<PreConselhoAmbienteEnsinoResponseDTO> preConselhoAmbienteEnsino,
        List<PreConselhoSupervisaoResponseDTO> preConselhoSupervisores
) {
}
