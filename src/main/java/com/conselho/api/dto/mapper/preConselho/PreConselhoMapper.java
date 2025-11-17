package com.conselho.api.dto.mapper.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.ConselhoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PreConselhoMapper {

    private ConselhoRepository conselhoRepository;
    public PreConselho paraEntidade(PreConselhoRequestDTO request){
        PreConselho preConselho = new PreConselho();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        preConselho.setConselho(conselho);

        return preConselho;
    }

    public PreConselhoResponseDTO paraResposta(PreConselho preConselho){
        return new PreConselhoResponseDTO(
                preConselho.getId(),
                preConselho.getConselho().getId()
        );
    }

    public PreConselho verificarUpdate(PreConselhoRequestDTO request, PreConselho preConselho){
        if (request.idConselho() != null && (preConselho.getConselho() == null || !request.idConselho().equals(preConselho.getConselho().getId()))){
            Conselho conselho = conselhoRepository.findById(request.idConselho())
                    .orElseThrow(ConselhoNaoExiste::new);

            preConselho.setConselho(conselho);
        }

        return preConselho;
    }
}
