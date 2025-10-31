package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoMapper;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.representante.RepresentanteNaoExiste;
import com.conselho.api.exception.turma.TurmaNaoExiste;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConselhoService {
    private ConselhoMapper mapper;
    private ConselhoRepository conselhoRepository;
    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private PedagogicoRepository pedagogicoRepository;

    // CREATE
    public ConselhoResponseDTO criarConselho(ConselhoRequestDTO request){
        Conselho conselho = mapper.paraEntidade(request);

        // VERIFICAÇÃO SE CADA ID DAS CHAVES ESTRANGEIRAS EXISTEM
        conselho.setTurma(turmaRepository.findById(request.idTurma())
                        .orElseThrow(TurmaNaoExiste::new));

        conselho.setRepresentante1(alunoRepository.findById(request.idRepresentante1())
                        .orElseThrow(RepresentanteNaoExiste::new));

        conselho.setRepresentante2(alunoRepository.findById(request.idRepresentante2())
                        .orElseThrow(RepresentanteNaoExiste::new));

        conselho.setPedagogico(pedagogicoRepository.findById(request.idPedagogico())
                        .orElseThrow(PedagogicoNaoExiste::new));

        Conselho salvo = conselhoRepository.save(conselho);

        return mapper.paraResposta(salvo);
    }

    // BUSCAR TODOS
    public List<ConselhoResponseDTO> listarConselhos(){
        return conselhoRepository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoResponseDTO buscarConselhoPorId(Long id){
        Conselho conselhoEncontrado = conselhoRepository.findById(id)
                .orElseThrow(ConselhoNaoExiste::new);

        return mapper.paraResposta(conselhoEncontrado);
    }

    // UPDATE
    public ConselhoResponseDTO atualizarConselho (Long id, ConselhoRequestDTO request){
        Conselho conselhoEncontrado = conselhoRepository.findById(id)
                .orElseThrow(ConselhoNaoExiste::new);
        Conselho conselhoAtualizado = mapper.verificarUpdate(request, conselhoEncontrado);

        return mapper.paraResposta(conselhoRepository.save(conselhoAtualizado));
    }

    // DELETE
    public void deletarConselho(Long id){
        if (!conselhoRepository.existsById(id)){
            throw new ConselhoNaoExiste();
        }
        conselhoRepository.deleteById(id);
    }
}
