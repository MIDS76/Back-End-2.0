package com.conselho.api.dto.mapper.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoPedagogicoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoPedagogicoResponseDTO;
import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PreConselhoPedagogicoMapper {

    private PreConselhoAmbienteEnsinoRepository repository;

    public PreConselhoPedagogico paraEntidade(PreConselhoPedagogicoRequestDTO requestDTO) {
        PreConselhoPedagogico preConselhoPedagogico = new PreConselhoPedagogico();

        PreConselho preConselho = new PreConselho();
        preConselho.setId(requestDTO.idPreConselho());

        preConselhoPedagogico.setPreConselho(preConselho);
        preConselhoPedagogico.setPontosPositivos(preConselhoPedagogico.getPontosPositivos());
        preConselhoPedagogico.setPontosMelhoria(preConselhoPedagogico.getPontosMelhoria());
        preConselhoPedagogico.setSugestoes(preConselhoPedagogico.getSugestoes());

        return preConselhoPedagogico;
    }

    public PreConselhoPedagogicoResponseDTO paraResposta(PreConselhoPedagogico preConselhoPedagogico) {
        return new PreConselhoPedagogicoResponseDTO(
                preConselhoPedagogico.getId(),
                preConselhoPedagogico.getPreConselho().getId(),
                preConselhoPedagogico.getPontosPositivos(),
                preConselhoPedagogico.getPontosMelhoria(),
                preConselhoPedagogico.getSugestoes()
        );
    }

    public PreConselhoPedagogico paraUpdate(PreConselhoPedagogicoRequestDTO requestDTO, PreConselhoPedagogico preConselhoPedagogico) {
        if ((requestDTO.idPreConselho() != null && (preConselhoPedagogico.getPreConselho() == null || !requestDTO.idPreConselho().equals(preConselhoPedagogico.getPreConselho().getId())))) {
            preConselhoPedagogico.setId(requestDTO.idPreConselho());
        }
        if ((requestDTO.pontosPositivos() != null && !requestDTO.pontosPositivos().equals(preConselhoPedagogico.getPontosPositivos()))) {
            preConselhoPedagogico.setPontosPositivos(requestDTO.pontosPositivos());
        }
        if ((requestDTO.pontosMelhoria() != null && !requestDTO.pontosMelhoria().equals(preConselhoPedagogico.getPontosMelhoria()))) {
            preConselhoPedagogico.setPontosMelhoria(requestDTO.pontosMelhoria());
        }
        if ((requestDTO.sugestoes() != null && !requestDTO.sugestoes().equals(preConselhoPedagogico.getSugestoes()))) {
            preConselhoPedagogico.setSugestoes(requestDTO.sugestoes());
        }

        return preConselhoPedagogico;
    }
}
