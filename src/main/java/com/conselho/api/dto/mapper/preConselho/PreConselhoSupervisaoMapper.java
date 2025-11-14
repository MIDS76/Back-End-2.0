package com.conselho.api.dto.mapper.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoSupervisaoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoSupervisaoResponseDTO;
import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.preConselho.PreConselhoSupervisaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PreConselhoSupervisaoMapper {

    private PreConselhoSupervisaoRepository repository;

    public PreConselhoSupervisao paraEntidade(PreConselhoSupervisaoRequestDTO requestDTO) {
        PreConselhoSupervisao preConselhoSupervisao = new PreConselhoSupervisao();

        PreConselho preConselho = new PreConselho();
        preConselho.setId(requestDTO.idPreConselho());

        preConselhoSupervisao.setPreConselho(preConselho);
        preConselhoSupervisao.setPontosPositivos(preConselhoSupervisao.getPontosPositivos());
        preConselhoSupervisao.setPontosMelhoria(preConselhoSupervisao.getPontosMelhoria());
        preConselhoSupervisao.setSugestoes(preConselhoSupervisao.getSugestoes());

        return preConselhoSupervisao;
    }

    public PreConselhoSupervisaoResponseDTO paraResposta(PreConselhoSupervisao preConselhoSupervisao) {
        return new PreConselhoSupervisaoResponseDTO(
                preConselhoSupervisao.getId(),
                preConselhoSupervisao.getPreConselho().getId(),
                preConselhoSupervisao.getPontosPositivos(),
                preConselhoSupervisao.getPontosMelhoria(),
                preConselhoSupervisao.getSugestoes()
        );
    }

    public PreConselhoSupervisao paraUpdate(PreConselhoSupervisaoRequestDTO requestDTO, PreConselhoSupervisao preConselhoSupervisao) {
        if ((requestDTO.idPreConselho() != null && (preConselhoSupervisao.getPreConselho() == null || !requestDTO.idPreConselho().equals(preConselhoSupervisao.getPreConselho().getId())))){
            preConselhoSupervisao.setId(requestDTO.idPreConselho());
        }
        if (requestDTO.pontosPositivos() != null && !requestDTO.pontosPositivos().equals(preConselhoSupervisao.getPontosPositivos())) {
            preConselhoSupervisao.setPontosPositivos(requestDTO.pontosPositivos());
        }
        if ((requestDTO.pontosMelhoria() != null && !requestDTO.pontosMelhoria().equals(preConselhoSupervisao.getPontosMelhoria()))){
            preConselhoSupervisao.setPontosMelhoria(requestDTO.pontosMelhoria());
        }
        if (requestDTO.sugestoes() != null && !requestDTO.sugestoes().equals(preConselhoSupervisao.getSugestoes())) {
            preConselhoSupervisao.setSugestoes(requestDTO.sugestoes());
        }
        return preConselhoSupervisao;
    }
}