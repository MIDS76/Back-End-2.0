package com.conselho.api.dto.response;

import com.conselho.api.dto.response.feedback.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.dto.response.feedback.ConselhoTurmaFeedbackResponseDTO;

import java.util.List;

public record ConselhoFeedbacksResponseDTO (
        List<ConselhoAlunoFeedbackResponseDTO> alunoFeedbackResponseDTO,
        List<ConselhoTurmaFeedbackResponseDTO> turmaFeedbackResponseDTO
){
}
