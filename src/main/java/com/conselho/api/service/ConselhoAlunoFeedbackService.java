package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoAlunoFeedbackMapper;
import com.conselho.api.dto.request.ConselhoAlunoFeedbackRequestDTO;
import com.conselho.api.dto.response.ConselhoAlunoFeedbackResponseDTO;
import com.conselho.api.model.ConselhoAlunoFeedback;
import com.conselho.api.repository.ConselhoAlunoFeedbackRepository;
import com.conselho.api.repository.PedagogicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConselhoAlunoFeedbackService {
    private ConselhoAlunoFeedbackMapper mapper;
    private ConselhoAlunoFeedbackRepository repository;
    private PedagogicoRepository pedagogicoRepository;
    private
    public ConselhoAlunoFeedbackResponseDTO create (ConselhoAlunoFeedbackRequestDTO request){
        ConselhoAlunoFeedback conselhoAlunoFeedback = mapper.paraEntidade(request);

        conselhoAlunoFeedback.setPedagogico(pedagogicoRepository.getReferenceById(request.idPedagogico()));
    }
}
