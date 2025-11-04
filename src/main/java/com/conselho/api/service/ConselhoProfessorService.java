package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoProfessorMapper;
import com.conselho.api.dto.mapper.ProfessorMapper;
import com.conselho.api.dto.request.ConselhoProfessorRequest;
import com.conselho.api.dto.response.ConselhoProfessorResponse;
import com.conselho.api.dto.response.ProfessorResponse;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselhoProfessor.ConselhoProfessorNaoExiste;
import com.conselho.api.exception.professor.ProfessorNaoExisteException;
import com.conselho.api.model.ConselhoProfessor;
import com.conselho.api.repository.ConselhoProfessorRespository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ConselhoProfessorService {

    private ConselhoProfessorMapper mapper;
    private ProfessorMapper professorMapper;
    private ConselhoRepository conselhoRepository;
    private ProfessorRepository professorRepository;
    private ConselhoProfessorRespository conselhoProfessorRespository;

    // CREATE
    public ConselhoProfessorResponse criarConselhoProfessor (ConselhoProfessorRequest request){
        ConselhoProfessor conselhoProfessor = mapper.paraEntidade(request);

        conselhoProfessor.setConselho(conselhoRepository.findById(request.idConselho())
                .orElseThrow(ConselhoNaoExiste::new));

        conselhoProfessor.setProfessor(professorRepository.findById(request.idProfessor())
                .orElseThrow(ProfessorNaoExisteException::new));

        ConselhoProfessor conselhoProfessorSalvo = conselhoProfessorRespository.save(conselhoProfessor);

        return mapper.paraResposta(conselhoProfessorSalvo);
    }

    // DELETE
    public void deletarConselhoProfessor(Long id){
        if (conselhoProfessorRespository.existsById(id)){
            throw new ConselhoProfessorNaoExiste();
        }

        conselhoProfessorRespository.deleteById(id);
    }

    // BUSCAR TODOS
    public List<ConselhoProfessorResponse> buscarTodos(){
        return conselhoProfessorRespository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoProfessorResponse buscarPorId(Long id){
        ConselhoProfessor conselhoProfessor = conselhoProfessorRespository.findById(id)
                .orElseThrow(ConselhoProfessorNaoExiste::new);

        return mapper.paraResposta(conselhoProfessor);
    }

    // ATUALIZAR
    public ConselhoProfessorResponse update(Long id, ConselhoProfessorRequest request){
        ConselhoProfessor conselhoProfessor = conselhoProfessorRespository.findById(id)
                .orElseThrow(ConselhoProfessorNaoExiste::new);

        ConselhoProfessor conselhoProfessorAtualizado = mapper.verificarUpdate(request, conselhoProfessor);

        return mapper.paraResposta(conselhoProfessorRespository.save(conselhoProfessorAtualizado));
    }

    // BUSCAR PROFESSORES POR UM CONSELHO ESPECIFICO
    public List<ProfessorResponse> buscarProfessoresPorConselho(Long id){
        return conselhoProfessorRespository.findByConselhoId(id)
                .stream()
                .map(rel -> professorMapper.paraRespostaProfessor(rel.getProfessor()))
                .toList();
    }
}
