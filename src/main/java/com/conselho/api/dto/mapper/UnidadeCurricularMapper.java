package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.UnidadeCurricularRequestDTO;
import com.conselho.api.dto.response.UnidadeCurricularResponse;
import com.conselho.api.model.unidadeCurricular.UnidadeCurricular;
import org.springframework.stereotype.Component;

@Component
public class UnidadeCurricularMapper {

    public UnidadeCurricular paraEntidade(UnidadeCurricularRequestDTO unidadeCurricularRequestDTO) {
        return new UnidadeCurricular(unidadeCurricularRequestDTO.nome());
    }

    public UnidadeCurricularResponse paraResposta(UnidadeCurricular unidadeCurricular) {
        return new UnidadeCurricularResponse(unidadeCurricular.getId(), unidadeCurricular.getNome());
    }

    public UnidadeCurricular paraUpdate(UnidadeCurricularRequestDTO unidadeCurricularRequestDTO, UnidadeCurricular unidadeCurricular) {
        if ((unidadeCurricularRequestDTO.nome() != unidadeCurricular.getNome() && unidadeCurricularRequestDTO.nome() != null)) {
            unidadeCurricular.setNome(unidadeCurricularRequestDTO.nome());

        }
        return unidadeCurricular;
    }
}
