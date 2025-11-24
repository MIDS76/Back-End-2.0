package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoProfessorMapper;
import com.conselho.api.dto.request.AtualizarPreConselhoProfessorRequestDTO;
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
import jakarta.transaction.Transactional;
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

    @Transactional
    public void criarPreConselhoProfessor(Long idConselho, Long idPreConselho) {

        // BUSCA TODAS AS UC + PROFESSOR DO CONSELHO
        List<UcProfessor> listaUcProfessor = ucProfessorRepository.findByConselhoId(idConselho);

        if (listaUcProfessor.isEmpty()) {
            throw new RuntimeException("Nenhuma UC encontrada para este conselho");
        }

        PreConselho preConselho = preConselhoRepository.findById(idPreConselho)
                .orElseThrow(PreConselhoNaoExisteException::new);

        List<PreConselhoProfessor> listaSalvar = new ArrayList<>();

        for (UcProfessor up : listaUcProfessor) {

            PreConselhoProfessor pre = new PreConselhoProfessor();

            pre.setPreConselho(preConselho);
            pre.setProfessor(up.getProfessor());
            pre.setUnidadeCurricular(up.getUnidadeCurricular());

            // CAMPOS EM BRANCO
            pre.setPontosPositivos("");
            pre.setPontoMelhoria("");
            pre.setSugestoes("");

            listaSalvar.add(pre);
        }

        preConselhoProfessorRepository.saveAll(listaSalvar);
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

    public PreConselhoProfessorResponseDTO atualizarPreConselhoProfessor(Long id, AtualizarPreConselhoProfessorRequestDTO request) {
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
