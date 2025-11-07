package com.conselho.api.service;

import com.conselho.api.dto.mapper.PreConselhoSupervisaoMapper;
import com.conselho.api.dto.request.PreConselhoRequest;
import com.conselho.api.dto.request.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.PreConselhoResponse;
import com.conselho.api.dto.response.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselho.DataForaDoPeriodoConselhoException;
import com.conselho.api.exception.preConselho.PreConselhoExisteException;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoSupervisao.PreConselhoSupervisaoJaExisteException;
import com.conselho.api.exception.preConselhoSupervisao.PreConselhoSupervisaoNaoExisteException;
import com.conselho.api.model.PreConselhoSupervisao;
import com.conselho.api.repository.PreConselhoSupervisaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoSupervisaoService {

    private PreConselhoSupervisaoRepository repository;
    private PreConselhoSupervisaoMapper mapper;
    public PreConselhoSupervisaoResponseDTO criarPreConselhoSupervisao(PreConselhoSupervisaoRequestDTO requestDTO) {

        PreConselhoSupervisao preConselhoSupervisao = repository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoSupervisaoNaoExisteException::new);

        if (repository.existsByPreConselhoId(requestDTO.idPreConselho())) {
            throw new PreConselhoSupervisaoJaExisteException();
        }
        PreConselhoSupervisao preConSupervisao = mapper.paraEntidade(requestDTO);
        preConSupervisao.setId(preConselhoSupervisao.getId());

        PreConselhoSupervisao salvarPreConselhoSupervisao = repository.save(preConSupervisao);

        return mapper.paraResposta(salvarPreConselhoSupervisao);
    }

    public List<PreConselhoSupervisaoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public PreConselhoSupervisaoResponseDTO buscarPorId(Long id) {
        PreConselhoSupervisao preConselhoSupervisao = repository.findById(id)
                .orElseThrow(PreConselhoSupervisaoNaoExisteException::new);

        return mapper.paraResposta(preConselhoSupervisao);
    }

    public PreConselhoSupervisaoResponseDTO atualizarPreConselhoSupervisao(Long id, PreConselhoSupervisaoRequestDTO requestDTO) {
        PreConselhoSupervisao preConselhoSupervisao = repository.findById(id)
                .orElseThrow(PreConselhoSupervisaoNaoExisteException::new);

        PreConselhoSupervisao preConselhoSupervisaoAtualizado = mapper.verificarUpdate(requestDTO, preConselhoSupervisao);

        return mapper.paraResposta(preConselhoSupervisaoAtualizado);
    }

    public void deletarPreConselhoSupervisao(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoSupervisaoNaoExisteException();
        }
    }

}
