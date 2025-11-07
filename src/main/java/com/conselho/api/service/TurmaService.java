package com.conselho.api.service;

import com.conselho.api.dto.mapper.TurmaMapper;
import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.exception.turma.TurmaNaoExiste;
import com.conselho.api.model.entity.Turma;
import com.conselho.api.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TurmaService {

    private TurmaMapper mapper;
    private TurmaRepository repository;


    public TurmaResponseDTO criarTurma(TurmaRequestDTO request){
        Turma newTurma = repository.save(mapper.paraEntidade(request));
        return mapper.paraResposta(newTurma);
    }

    public List<TurmaResponseDTO> listarTurmas(){
        return repository.findAll()
                .stream().map(mapper :: paraResposta)
                .toList();
    }

    public TurmaResponseDTO buscarTurmaPorId(Long idTurma){
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new TurmaNaoExiste());

        return mapper.paraResposta(turma);
    }

    public TurmaResponseDTO atualizarTurma(Long idTurma, TurmaRequestDTO request){
        Turma turma = repository.findById(idTurma).orElseThrow(() ->
                new TurmaNaoExiste());

        Turma newTurma = mapper.paraUpdate(request, turma);
        return mapper.paraResposta(newTurma);
    }


    public void deletarTurma(Long idTurma) {
        repository.findById(idTurma).orElseThrow(() ->
                new TurmaNaoExiste());

        repository.deleteById(idTurma);
    }
}