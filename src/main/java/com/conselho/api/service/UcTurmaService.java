package com.conselho.api.service;

import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.Professor;
import com.conselho.api.model.UcTurma;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import com.conselho.api.repository.UcTurmaRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UcTurmaService {

        private UcTurmaRepository ucTurmaRepository;
        private ConselhoRepository conselhoRepository;
        private ProfessorRepository professorRepository;
        private UnidadeCurricularRepository unidadeCurricularRepository;

        public UcTurma criarUcTurma(Long idConselho, Long idProfessor, Long idUnidadeCurricular) {
            Conselho conselho = conselhoRepository.findById(idConselho)
                    .orElseThrow(() -> new ConselhoNaoExiste());

            Professor professor = professorRepository.findById(idProfessor)
                    .orElseThrow(() -> new ProfessorNaoExisteException());

            UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(idUnidadeCurricular)
                    .orElseThrow(() -> new UnidadeCurricularNaoExisteException());

            UcTurma ucTurma = new UcTurma();
            ucTurma.setConselho(conselho);
            ucTurma.setProfessor(professor);
            ucTurma.setUnidadeCurricular(unidadeCurricular);

            return ucTurmaRepository.save(ucTurma);
        }

        public UcTurma atualizarUcTurma(Long id, Long idConselho, Long idProfessor, Long idUnidadeCurricular) {
            UcTurma ucTurma = ucTurmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UC_Turma não encontrada"));

            Conselho conselho = conselhoRepository.findById(idConselho)
                    .orElseThrow(() -> new ConselhoNaoExiste());

            Professor professor = professorRepository.findById(idProfessor)
                    .orElseThrow(() -> new ProfessorNaoExisteException());

            UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(idUnidadeCurricular)
                    .orElseThrow(() -> new UnidadeCurricularNaoExisteException());

            ucTurma.setConselho(conselho);
            ucTurma.setProfessor(professor);
            ucTurma.setUnidadeCurricular(unidadeCurricular);

            return ucTurmaRepository.save(ucTurma);
        }

        public UcTurma buscarUcTurmaPorId(Long id) {
            return ucTurmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UC_Turma não encontrada"));
        }

        public void deletarUcTurma(Long id) {
            UcTurma ucTurma = buscarUcTurmaPorId(id);
            ucTurmaRepository.delete(ucTurma);
        }

        public List<UcTurma> listarUcTurma() {
            return ucTurmaRepository.findAll();
        }
}


