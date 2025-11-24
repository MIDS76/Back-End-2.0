package com.conselho.api.service;

import com.conselho.api.dto.mapper.UcProfessorMapper;
import com.conselho.api.dto.request.UcProfessorRequestDTO;
import com.conselho.api.dto.response.UcProfessorResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.exception.ucProfessor.UcProfessorNaoExisteException;
import com.conselho.api.exception.unidadeCurricular.UnidadeCurricularNaoExisteException;
import com.conselho.api.model.UcProfessor;
import com.conselho.api.model.UnidadeCurricular;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Professor;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.ProfessorRepository;
import com.conselho.api.repository.UcProfessorRepository;
import com.conselho.api.repository.UnidadeCurricularRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UcProfessorService {

        private UcProfessorRepository ucProfessorRepository;
        private ConselhoRepository conselhoRepository;
        private ProfessorRepository professorRepository;
        private UnidadeCurricularRepository unidadeCurricularRepository;
        private UcProfessorMapper ucProfessorMapper;

        public UcProfessorResponseDTO criarUcProfessor(UcProfessorRequestDTO requestDTO) {
            Conselho conselho = conselhoRepository.findById(requestDTO.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            Professor professor = professorRepository.findById(requestDTO.idProfessor())
                    .orElseThrow(ProfessorNaoExisteException::new);

            List<Long> idsUcs = requestDTO.idUcs();

            List<UnidadeCurricular> ucs =
                    unidadeCurricularRepository.findByIdIn(idsUcs);

            if (ucs.size() != idsUcs.size()) {
                throw new UnidadeCurricularNaoExisteException();
            }

            List<String> nomesUcs =
                    unidadeCurricularRepository.findNomesByIds(idsUcs);

            List<UcProfessor> listaUcProfessor = new ArrayList<>();

            for (UnidadeCurricular uc : ucs) {

                UcProfessor ucProfessor = new UcProfessor();
                ucProfessor.setConselho(conselho);
                ucProfessor.setProfessor(professor);
                ucProfessor.setUnidadeCurricular(uc);

                listaUcProfessor.add(ucProfessor);
            }

            ucProfessorRepository.saveAll(listaUcProfessor);

            return ucProfessorMapper.paraRespostaComLista(
                    listaUcProfessor.get(0),
                    nomesUcs
            );
        }

    public List<UcProfessorResponseDTO> listarUcProfessor() {
        List<UcProfessor> ucProfessores = ucProfessorRepository.findAll();
        List<UcProfessorResponseDTO> responseList = new ArrayList<>();

        for (UcProfessor ucProfessor : ucProfessores) {
            // Verifica se a Unidade Curricular está presente
            if (ucProfessor.getUnidadeCurricular() != null) {
                Long unidadeCurricularId = ucProfessor.getUnidadeCurricular().getId();
                List<String> nomesUcs = unidadeCurricularRepository.findNomesByIds(Collections.singletonList(unidadeCurricularId));

                UcProfessorResponseDTO dto = ucProfessorMapper.paraRespostaComLista(ucProfessor, nomesUcs);
                responseList.add(dto);
            } else {
                // Caso não tenha Unidade Curricular, faz o tratamento necessário, por exemplo, logar ou ignorar.
                // Por enquanto, apenas ignora este registro (não inclui na lista de resposta).
                // Se for necessário, pode-se adicionar algum valor padrão ou um erro na resposta.
                System.out.println("UcProfessor sem Unidade Curricular: " + ucProfessor.getId());
            }
        }

        return responseList;
    }




    public UcProfessorResponseDTO buscarUcProfessorPorId(Long id) {
        // Busca o UcProfessor pelo ID
        UcProfessor ucProfessor = ucProfessorRepository.findById(id)
                .orElseThrow(UcProfessorNaoExisteException::new);

        // Pega o ID da Unidade Curricular associada ao UcProfessor
        Long unidadeCurricularId = ucProfessor.getUnidadeCurricular().getId();

        // Busca os nomes das Unidades Curriculares associadas
        List<String> nomesUcs = unidadeCurricularRepository.findNomesByIds(Collections.singletonList(unidadeCurricularId));

        // Mapeia o UcProfessor para o DTO com os nomes das UCs
        return ucProfessorMapper.paraRespostaComLista(
                ucProfessor,
                nomesUcs
        );
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


