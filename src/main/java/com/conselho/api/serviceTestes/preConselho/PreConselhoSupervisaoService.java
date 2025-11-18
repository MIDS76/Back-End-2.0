package com.conselho.api.serviceTestes.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoSupervisaoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoSupervisao.PreConselhoSupervisaoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import com.conselho.api.repository.preConselho.PreConselhoSupervisaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoSupervisaoService {

    private PreConselhoSupervisaoRepository repository;
    private PreConselhoSupervisaoMapper mapper;
    private PreConselhoRepository preConselhoRepository;

    public PreConselhoSupervisaoResponseDTO criarPreConselhoSupervisao(PreConselhoSupervisaoRequestDTO requestDTO) {
        PreConselhoSupervisao preConSupervisao = mapper.paraEntidade(requestDTO);

        preConSupervisao.setPreConselho(preConselhoRepository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoNaoExisteException::new));

        preConSupervisao.setPontosPositivos(requestDTO.pontosPositivos());
        preConSupervisao.setPontosMelhoria(requestDTO.pontosMelhoria());
        preConSupervisao.setSugestoes(requestDTO.sugestoes());

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

        PreConselhoSupervisao preConselhoSupervisaoAtualizado = mapper.paraUpdate(requestDTO, preConselhoSupervisao);

        return mapper.paraResposta(preConselhoSupervisaoAtualizado);
    }

    public void deletarPreConselhoSupervisao(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoSupervisaoNaoExisteException();
        }
        repository.deleteById(id);
    }

}
