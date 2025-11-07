package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoAmbienteEnsinoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoJaExiseException;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoNaoExiseException;
import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoAmbienteEnsinoService {
    private PreConselhoAmbienteEnsinoRepository repository;
    private PreConselhoAmbienteEnsinoMapper mapper;

    public PreConselhoAmbienteEnsinoResponseDTO criarPreConselhoAmbienteEnsino(PreConselhoAmbienteEnsinoRequestDTO requestDTO){

        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = repository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoAmbienteEnsinoNaoExiseException::new);

        if (repository.existsByPreConselhoId(requestDTO.idPreConselho())) {
            throw new PreConselhoAmbienteEnsinoJaExiseException();
        }
        PreConselhoAmbienteEnsino preConAmbienteEnsino = mapper.paraEntidade(requestDTO);
        preConAmbienteEnsino.setId(preConselhoAmbienteEnsino.getId());

        PreConselhoAmbienteEnsino salvarPreConselhoAmbienteEnsino= repository.save(preConAmbienteEnsino);

        return mapper.paraResposta(salvarPreConselhoAmbienteEnsino);
    }

    public List<PreConselhoAmbienteEnsinoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    public PreConselhoAmbienteEnsinoResponseDTO buscarPorId(Long id) {
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = repository.findById(id)
                .orElseThrow(PreConselhoAmbienteEnsinoNaoExiseException::new);

        return mapper.paraResposta(preConselhoAmbienteEnsino);
    }

    public PreConselhoAmbienteEnsinoResponseDTO atualizarPreConselhoAmbienteEnsino(Long id, PreConselhoAmbienteEnsinoRequestDTO requestDTO) {
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = repository.findById(id)
                .orElseThrow(PreConselhoAmbienteEnsinoNaoExiseException::new);

        PreConselhoAmbienteEnsino preConselhoAmbienteEnsinoAtualizado = mapper.verificarUpdate(requestDTO,preConselhoAmbienteEnsino);

        return mapper.paraResposta(preConselhoAmbienteEnsinoAtualizado);
    }

    public void deletarPreConselhoAmbienteEnsino(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoAmbienteEnsinoNaoExiseException();
        }
    }
}
