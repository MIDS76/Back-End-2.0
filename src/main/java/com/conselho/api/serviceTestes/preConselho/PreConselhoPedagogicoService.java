package com.conselho.api.serviceTestes.preConselho;

import com.conselho.api.dto.mapper.preConselho.PreConselhoPedagogicoMapper;
import com.conselho.api.dto.request.preConselho.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.exception.preConselho.PreConselhoNaoExisteException;
import com.conselho.api.exception.preConselhoPedagogico.PreConselhoPedagogicoNaoExisteException;
import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import com.conselho.api.repository.preConselho.PreConselhoPedagogicoRepository;
import com.conselho.api.repository.preConselho.PreConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreConselhoPedagogicoService {

    private PreConselhoPedagogicoRepository repository;
    private PreConselhoPedagogicoMapper mapper;
    private PreConselhoRepository preConselhoRepository;

    public PreConselhoPedagogicoResponseDTO criarPreConselhoPedagogico(PreConselhoPedagogicoRequestDTO requestDTO){
        PreConselhoPedagogico preConPedagogico = mapper.paraEntidade(requestDTO);

        preConPedagogico.setPreConselho(preConselhoRepository.findById(requestDTO.idPreConselho())
                .orElseThrow(PreConselhoNaoExisteException::new));

        preConPedagogico.setPontosPositivos(requestDTO.pontosPositivos());
        preConPedagogico.setPontosMelhoria(requestDTO.pontosMelhoria());
        preConPedagogico.setSugestoes(requestDTO.sugestoes());

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
                .orElseThrow(PreConselhoPedagogicoNaoExisteException::new);

        return mapper.paraResposta(preConselhoPedagogico);
    }

    public PreConselhoPedagogicoResponseDTO atualizarPreConselhoPedagogico(Long id, PreConselhoPedagogicoRequestDTO requestDTO) {
        PreConselhoPedagogico preConselhoPedagogico = repository.findById(id)
                .orElseThrow(PreConselhoPedagogicoNaoExisteException::new);

        PreConselhoPedagogico preConselhoPedagogicoAtualizado = mapper.paraUpdate(requestDTO,preConselhoPedagogico);

        return mapper.paraResposta(repository.save(preConselhoPedagogicoAtualizado));
    }

    public void deletarPreConselhoPedagogico(Long id) {
        if (!repository.existsById(id)) {
            throw new PreConselhoPedagogicoNaoExisteException();
        }
        repository.deleteById(id);
    }
}
