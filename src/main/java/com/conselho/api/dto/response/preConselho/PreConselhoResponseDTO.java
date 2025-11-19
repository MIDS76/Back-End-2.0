package com.conselho.api.dto.response.preConselho;

import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import java.util.List;

public record PreConselhoResponseDTO(
        Long id,
        Long idConselho
) {
}
