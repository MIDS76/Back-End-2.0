package com.conselho.api.service;

import com.conselho.api.dto.mapper.PreConselhoPedagogicoMapper;
import com.conselho.api.dto.request.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.request.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.dto.response.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoJaExiseException;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoNaoExiseException;
import com.conselho.api.exception.preConselhoPedagogico.PreConselhoPedagogicoNaoExiseException;
import com.conselho.api.model.PreConselhoAmbienteEnsino;
import com.conselho.api.model.PreConselhoPedagogico;
import com.conselho.api.repository.PreConselhoPedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoPedagogicoService {

    private PreConselhoPedagogicoRepository repository;
    private PreConselhoPedagogicoMapper mapper;

    public PreConselhoPedagogicoResponseDTO criarPreConselhoPedagogico(PreConselhoPedagogicoRequestDTO requestDTO){

        PreConselhoPedagogico preConselhoPedagogico = repository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoPedagogicoNaoExiseException::new);

        if (repository.existsByPreConselhoId(requestDTO.idPreConselho())) {
            throw new PreConselhoAmbienteEnsinoJaExiseException();
        }
        PreConselhoPedagogico preConPedagogico = mapper.paraEntidade(requestDTO);
        preConPedagogico.setId(preConselhoPedagogico.getId());

        PreConselhoPedagogico salvarPreConselhoPedagogico= repository.save(preConPedagogico);

        return mapper.paraResposta(salvarPreConselhoPedagogico);
    }

    public List<PreConselhoPedagogicoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public PreConselhoPedagogicoResponseDTO buscarPorId(Long id) {
        PreConselhoPedagogico preConselhoPedagogico = repository.findById(id)
                .orElseThrow(PreConselhoPedagogicoNaoExiseException::new);

        return mapper.paraResposta(preConselhoPedagogico);
    }

    public PreConselhoPedagogicoResponseDTO atualizarPreConselhoPedagogico(Long id, PreConselhoPedagogicoRequestDTO requestDTO) {
        PreConselhoPedagogico preConselhoPedagogico = repository.findById(id)
                .orElseThrow(PreConselhoPedagogicoNaoExiseException::new);

        PreConselhoPedagogico preConselhoPedagogicoAtualizado = mapper.verificarUpdate(requestDTO,preConselhoPedagogico);

        return mapper.paraResposta(preConselhoPedagogicoAtualizado);
    }

    public void deletarPreConselhoPedagogico(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoPedagogicoNaoExiseException();
        }
    }
}
