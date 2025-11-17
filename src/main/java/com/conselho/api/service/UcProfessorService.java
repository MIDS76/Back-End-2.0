package com.conselho.api.service;

import com.conselho.api.dto.mapper.UcProfessorMapper;
import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.exception.ucProfessor.UcProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.UcProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UcProfessorService {

        private UcProfessorRepository ucProfessorRepository;
        private ConselhoRepository conselhoRepository;
        private ProfessorRepository professorRepository;
        private UnidadeCurricularRepository unidadeCurricularRepository;
        private UcProfessorMapper ucProfessorMapper;

        public UcProfessorResponseDTO criarUcProfessor(UcProfessorRequestDTO requestDTO) {
            UcProfessor ucProfessor = ucProfessorMapper.paraEntidade(requestDTO);

            ucProfessor.setConselho(conselhoRepository.findById(requestDTO.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new));

            ucProfessor.setProfessor(professorRepository.findById(requestDTO.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new));

            ucProfessor.setUnidadeCurricular(unidadeCurricularRepository.findById(requestDTO.idUnidadeCurricular())
                    .orElseThrow(UnidadeCurricularNaoExisteException::new));

            UcProfessor salvarUcProfessor = ucProfessorRepository.save(ucProfessor);
            return ucProfessorMapper.paraResposta(salvarUcProfessor);
        }

        public List<UcProfessorResponseDTO> listarUcProfessor() {
            return ucProfessorRepository.findAll()
                    .stream()
                    .map(ucProfessorMapper::paraResposta)
                    .toList();
        }

        public UcProfessorResponseDTO buscarUcProfessorPorId(Long id) {
            UcProfessor ucProfessor = ucProfessorRepository.findById(id)
                    .orElseThrow(UcProfessorNaoExisteException::new);

            return ucProfessorMapper.paraResposta(ucProfessor);
        }

        public UcProfessorResponseDTO atualizarUcProfessor(UcProfessorRequestDTO requestDTO, Long id) {
            UcProfessor ucProfessor = ucProfessorRepository.findById(id)
                    .orElseThrow(UcProfessorNaoExisteException::new);

            UcProfessor atualizadoUcPorfessor = ucProfessorMapper.paraUpdate(requestDTO,ucProfessor);

            return ucProfessorMapper.paraResposta(ucProfessorRepository.save(atualizadoUcPorfessor));
        }
        public void deletarUcProfessor(Long id) {
            if (!ucProfessorRepository.existsById(id)) {
                throw new UcProfessorNaoExisteException();
            }
            ucProfessorRepository.deleteById(id);
        }
}


