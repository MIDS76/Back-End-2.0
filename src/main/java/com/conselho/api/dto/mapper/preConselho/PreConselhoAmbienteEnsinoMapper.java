package com.conselho.api.dto.mapper.preConselho;

import com.conselho.api.dto.request.preConselho.PreConselhoAmbienteEnsinoRequestDTO;
import com.conselho.api.dto.response.preConselho.PreConselhoAmbienteEnsinoResponseDTO;
import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import com.conselho.api.model.preConselho.PreConselho;
import com.conselho.api.repository.preConselho.PreConselhoAmbienteEnsinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PreConselhoAmbienteEnsinoMapper {

    private PreConselhoAmbienteEnsinoRepository repository;

    public PreConselhoAmbienteEnsino paraEntidade(PreConselhoAmbienteEnsinoRequestDTO requestDTO){
        PreConselhoAmbienteEnsino preConselhoAmbienteEnsino = new PreConselhoAmbienteEnsino();

        PreConselho preConselho = new PreConselho();
        preConselho.setId(requestDTO.idPreConselho());

        preConselhoAmbienteEnsino.setPreConselho(preConselho);
        preConselhoAmbienteEnsino.setPontosPositivos(preConselhoAmbienteEnsino.getPontosPositivos());
        preConselhoAmbienteEnsino.setPontosMelhoria(preConselhoAmbienteEnsino.getPontosMelhoria());
        preConselhoAmbienteEnsino.setSugestoes(preConselhoAmbienteEnsino.getSugestoes());

        return preConselhoAmbienteEnsino;
    }

    public PreConselhoAmbienteEnsinoResponseDTO paraResposta(PreConselhoAmbienteEnsino preConselhoAmbienteEnsino){
        return new PreConselhoAmbienteEnsinoResponseDTO(
                preConselhoAmbienteEnsino.getId(),
                preConselhoAmbienteEnsino.getPreConselho().getId(),
                preConselhoAmbienteEnsino.getPontosPositivos(),
                preConselhoAmbienteEnsino.getPontosMelhoria(),
                preConselhoAmbienteEnsino.getSugestoes()
        );
    }

    public PreConselhoAmbienteEnsino verificarUpdate(PreConselhoAmbienteEnsinoRequestDTO requestDTO, PreConselhoAmbienteEnsino preConselhoAmbienteEnsino) {
        if ((requestDTO.idPreConselho() != null && (preConselhoAmbienteEnsino.getPreConselho() == null || !requestDTO.idPreConselho().equals(preConselhoAmbienteEnsino.getPreConselho().getId())))) {
            preConselhoAmbienteEnsino.setId(requestDTO.idPreConselho());
        }
        if ((requestDTO.pontosPositivos() != null && !requestDTO.pontosPositivos().equals(preConselhoAmbienteEnsino.getPontosPositivos()))) {
            preConselhoAmbienteEnsino.setPontosPositivos(requestDTO.pontosPositivos());
        }
        if ((requestDTO.pontosMelhoria() != null && !requestDTO.pontosMelhoria().equals(preConselhoAmbienteEnsino.getPontosMelhoria()))) {
            preConselhoAmbienteEnsino.setPontosMelhoria(requestDTO.pontosMelhoria());
        }
        if ((requestDTO.sugestoes() != null && !requestDTO.sugestoes().equals(preConselhoAmbienteEnsino.getSugestoes()))) {
            preConselhoAmbienteEnsino.setSugestoes(requestDTO.sugestoes());
        }

        return preConselhoAmbienteEnsino;
    }
}
