package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoProfessorRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoProfessor.PreConselhoProfessorNaoExisteException;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import com.conselho.api.repository.PreConselhoProfessorRepository;
import com.conselho.api.repository.UcProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoProfessorService {

    private PreConselhoProfessorMapper mapper;
    private PreConselhoRepository preConselhoRepository;
    private UnidadeCurricularRepository unidadeCurricularRepository;
    private ProfessorRepository professorRepository;
    private PreConselhoProfessorRepository preConselhoProfessorRepository;
    private UcProfessorRepository ucProfessorRepository;

//    public PreConselhoProfessorResponseDTO criarPreConselhoProfessor(PreConselhoProfessorRequestDTO request){
//        PreConselhoProfessor preConselhoProfessor = mapper.paraEntidade(request);
//
//        preConselhoProfessor.setPreConselho(preConselhoRepository.findById(request.idPreConselho())
//                .orElseThrow(PreConselhoNaoExisteException::new));
//
//        preConselhoProfessor.setUnidadeCurricular(unidadeCurricularRepository.findById(request.idUnidadeCurricular())
//                .orElseThrow(UnidadeCurricularNaoExisteException::new));
//
//        preConselhoProfessor.setProfessor(professorRepository.findById(request.idProfessor())
//                .orElseThrow(ProfessorNaoExisteException::new));
//
//        preConselhoProfessor.setPontosPositivos(request.pontosPositivos());
//        preConselhoProfessor.setPontoMelhoria(request.pontoMelhoria());
//        preConselhoProfessor.setSugestoes(request.sugestoes());
//
//        PreConselhoProfessor salvo = preConselhoProfessorRepository.save(preConselhoProfessor);
//        return mapper.paraResposta(salvo);
//    }

    public PreConselhoProfessorResponseDTO criarPreConselhoProfessor(Long idConselho, Long idPreConselho) {
        PreConselhoProfessor preConselhoProfessor = new PreConselhoProfessor();

        PreConselho preConselho = (preConselhoRepository.findById(idPreConselho)
                .orElseThrow(PreConselhoNaoExisteException::new));

        if (preConselho == null) {
            throw new PreConselhoNaoExisteException();
        }

        preConselhoProfessor.setPreConselho(preConselho);

        List<UcProfessor> ucProfessores = ucProfessorRepository.findAllByConselhoId(idConselho);

        PreConselhoProfessor salvo = new PreConselhoProfessor();
        List<PreConselhoProfessor> salvos = new ArrayList<>();

        for (UcProfessor u : ucProfessores) {
            UnidadeCurricular unidadeCurricular = (unidadeCurricularRepository.findById(u.getUnidadeCurricular().getId())
                    .orElseThrow(UnidadeCurricularNaoExisteException::new));

            preConselhoProfessor.setUnidadeCurricular(unidadeCurricular);

            Professor professor = (professorRepository.findById(u.getProfessor().getId())
                    .orElseThrow(ProfessorNaoExisteException::new));
            preConselhoProfessor.setProfessor(professor);

            salvo = preConselhoProfessorRepository.save(preConselhoProfessor);
            salvos.add(salvo);
        }
        return mapper.paraResposta(salvo);
    }

    public List<PreConselhoProfessorResponseDTO> listarPreConselhoProfessor() {
        return preConselhoProfessorRepository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public PreConselhoProfessorResponseDTO buscarPreConselhoProfessorPorId(Long id) {
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoProfessorNaoExisteException::new);

        return mapper.paraResposta(preConselhoProfessor);
    }

    public PreConselhoProfessorResponseDTO atualizarPreConselhoProfessor(Long id, PreConselhoProfessorRequestDTO request) {
        PreConselhoProfessor preConselhoProfessor = preConselhoProfessorRepository.findById(id)
                .orElseThrow(PreConselhoNaoExisteException::new);

        PreConselhoProfessor preConcelhoProfessorAtualizado = mapper.paraUpdate(request, preConselhoProfessor);

        return mapper.paraResposta(preConselhoProfessorRepository.save(preConcelhoProfessorAtualizado));
    }

    public void deletarConselho(Long id) {
        if (!preConselhoProfessorRepository.existsById(id)) {
            throw new ConselhoNaoExiste();
        }
        preConselhoProfessorRepository.deleteById(id);
    }
}
