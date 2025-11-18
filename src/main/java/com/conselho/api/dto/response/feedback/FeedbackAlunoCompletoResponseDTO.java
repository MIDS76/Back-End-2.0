package com.conselho.api.dto.response.feedback;

public record FeedbackAlunoCompletoResponseDTO(

        //Aluno
        Long idFeedbackAluno,
        Long idConselho,
        String nomePedagogico,
        String nomeAluno,
        String pontosPositivosAluno,
        String pontosMelhoriaAluno,
        String sugestaoAluno,

        //Turma
        Long idFeedBackTurma,
        String pontosPositivosTurma,
        String pontosMelhoriaTurma,
        String sugestaoTurma
) {
}
