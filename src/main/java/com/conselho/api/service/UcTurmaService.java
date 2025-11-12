package com.conselho.api.service;

import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
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

        public UcProfessor criarUcTurma(Long idConselho, Long idProfessor, Long idUnidadeCurricular) {
            Conselho conselho = conselhoRepository.findById(idConselho)
                    .orElseThrow(() -> new ConselhoNaoExiste());

            Professor professor = professorRepository.findById(idProfessor)
                    .orElseThrow(() -> new ProfessorNaoExisteException());

            UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(idUnidadeCurricular)
                    .orElseThrow(() -> new UnidadeCurricularNaoExisteException());

            UcProfessor ucProfessor = new UcProfessor();
            ucProfessor.setConselho(conselho);
            ucProfessor.setProfessor(professor);
            ucProfessor.setUnidadeCurricular(unidadeCurricular);

            return ucTurmaRepository.save(ucProfessor);
        }

        public UcProfessor atualizarUcTurma(Long id, Long idConselho, Long idProfessor, Long idUnidadeCurricular) {
            UcProfessor ucProfessor = ucTurmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UC_Turma não encontrada"));

            Conselho conselho = conselhoRepository.findById(idConselho)
                    .orElseThrow(() -> new ConselhoNaoExiste());

            Professor professor = professorRepository.findById(idProfessor)
                    .orElseThrow(() -> new ProfessorNaoExisteException());

            UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(idUnidadeCurricular)
                    .orElseThrow(() -> new UnidadeCurricularNaoExisteException());

            ucProfessor.setConselho(conselho);
            ucProfessor.setProfessor(professor);
            ucProfessor.setUnidadeCurricular(unidadeCurricular);

            return ucTurmaRepository.save(ucProfessor);
        }

        public UcProfessor buscarUcTurmaPorId(Long id) {
            return ucTurmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UC_Turma não encontrada"));
        }

        public void deletarUcTurma(Long id) {
            UcProfessor ucProfessor = buscarUcTurmaPorId(id);
            ucTurmaRepository.delete(ucProfessor);
        }

        public List<UcProfessor> listarUcTurma() {
            return ucTurmaRepository.findAll();
        }
}


