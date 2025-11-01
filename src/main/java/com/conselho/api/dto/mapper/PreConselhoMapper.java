package com.conselho.api.dto.mapper;

import com.conselho.api.dto.request.PreConselhoRequest;
import com.conselho.api.dto.response.PreConselhoResponse;
import com.conselho.api.model.PreConselho;
import com.conselho.api.model.conselho.Conselho;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PreConselhoMapper {
    public PreConselho paraEntidade(PreConselhoRequest request){
        PreConselho preConselho = new PreConselho();

        Conselho conselho = new Conselho();
        conselho.setId(request.idConselho());

        preConselho.setConselho(conselho);
        preConselho.setDataInicio(request.dataInicio());
        preConselho.setDataFim(request.dataFim());

        return preConselho;
    }

    public PreConselhoResponse paraResposta(PreConselho preConselho){
        return new PreConselhoResponse(
                preConselho.getId(),
                preConselho.getConselho().getId(),
                preConselho.getDataInicio(),
                preConselho.getDataFim()
        );
    }
}
