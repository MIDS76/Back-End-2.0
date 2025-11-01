package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.UnidadeCurricularRequest;
import com.conselho.api.dto.response.UnidadeCurricularResponse;
import com.conselho.api.model.UnidadeCurricular;
import org.springframework.stereotype.Component;

@Component
public class UnidadeCurricularMapper {

    public UnidadeCurricular paraEntidade(UnidadeCurricularRequest unidadeCurricularRequest) {
        return new UnidadeCurricular(unidadeCurricularRequest.nome());
    }

    public UnidadeCurricularResponse paraResposta(UnidadeCurricular unidadeCurricular) {
        return new UnidadeCurricularResponse(unidadeCurricular.getId(), unidadeCurricular.getNome());
    }

    public UnidadeCurricular paraUpdate(UnidadeCurricularRequest unidadeCurricularRequest, UnidadeCurricular unidadeCurricular) {
        if ((unidadeCurricularRequest.nome() != unidadeCurricular.getNome() && unidadeCurricularRequest.nome() != null)) {
            unidadeCurricular.setNome(unidadeCurricularRequest.nome());

        }
        return unidadeCurricular;
    }
}
