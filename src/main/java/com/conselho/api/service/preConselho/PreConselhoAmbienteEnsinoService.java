package com.conselho.api.service.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoAmbienteEnsinoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoAmbienteEnsino.PreConselhoAmbienteEnsinoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoAmbienteEnsinoService {
    private PreConselhoAmbienteEnsinoRepository repository;
    private PreConselhoAmbienteEnsinoMapper mapper;
    private PreConselhoRepository preConselhoRepository;

    public PreConselhoAmbienteEnsinoResponseDTO criarPreConselhoAmbienteEnsino(PreConselhoAmbienteEnsinoRequestDTO requestDTO){
        PreConselhoAmbienteEnsino preConAmbienteEnsino = mapper.paraEntidade(requestDTO);

        preConAmbienteEnsino.setPreConselho(preConselhoRepository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoNaoExisteException::new));

        preConAmbienteEnsino.setPontosPositivos(requestDTO.pontosPositivos());
        preConAmbienteEnsino.setPontosMelhoria(requestDTO.pontosMelhoria());
        preConAmbienteEnsino.setSugestoes(requestDTO.sugestoes());

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
                .orElseThrow(PreConselhoAmbienteEnsinoNaoExisteException::new);

        return mapper.paraResposta(preConselhoAmbienteEnsino);
    }

    public PreConselhoAmbienteEnsinoResponseDTO atualizarPreConselhoAmbienteEnsino(Long id, PreConselhoAmbienteEnsinoRequestDTO requestDTO) {
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = repository.findById(id)
                .orElseThrow(PreConselhoAmbienteEnsinoNaoExisteException::new);

        PreConselhoAmbienteEnsino preConselhoAmbienteEnsinoAtualizado = mapper.paraUpdate(requestDTO,preConselhoAmbienteEnsino);
        return mapper.paraResposta(preConselhoAmbienteEnsinoAtualizado);
    }

    public void deletarPreConselhoAmbienteEnsino(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoAmbienteEnsinoNaoExisteException();
        }
        repository.deleteById(id);
    }
}
