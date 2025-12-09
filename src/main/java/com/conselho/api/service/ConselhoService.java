package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoMapper;
import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.request.preConselho.PreConselhoRequestDTO;
import com.conselho.api.dto.response.ConselhoFeedbacksResponseDTO;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.exception.aluno.AlunoNaoExisteException;
import com.conselho.api.exception.alunoTurma.AlunoTurmaNaoExisteException;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselho.EtapaInvalidaException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.representante.RepresentanteNaoExiste;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.Turma;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.conselho.EtapasConselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.repository.AlunoTurmaRepository;
import com.conselho.api.repository.entity.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.entity.PedagogicoRepository;
import com.conselho.api.repository.TurmaRepository;
import com.conselho.api.service.entity.AlunoService;
import com.conselho.api.service.preConselho.PreConselhoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ConselhoService {
    private ConselhoMapper mapper;
    private ConselhoRepository conselhoRepository;
    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private PedagogicoRepository pedagogicoRepository;
    private PreConselhoService preConselhoService;
    private AlunoTurmaRepository alunoTurmaRepository;

    // CREATE
    public ConselhoResponseDTO criarConselho(ConselhoRequestDTO request) {
        Conselho conselho = mapper.paraEntidade(request);

        // VERIFICAÇÃO SE CADA ID DAS CHAVES ESTRANGEIRAS EXISTEM
        Turma turma = turmaRepository.findById(request.idTurma())
                .orElseThrow(TurmaNaoExisteException::new);
        conselho.setTurma(turma);

        List<AlunoTurma> alunosDaTurma = alunoTurmaRepository.findByTurmaId(request.idTurma());
        List<Long> idsAlunos = new ArrayList<>();
        for (AlunoTurma t : alunosDaTurma) {
            idsAlunos.add(t.getAluno().getId());
        }
        conselho.setRepresentante1(alunoRepository.findById(request.idRepresentante1())
                .orElseThrow(AlunoTurmaNaoExisteException::new));
        if (!idsAlunos.contains(conselho.getRepresentante1().getId())) {
            throw new RepresentanteNaoExiste();
        } else {
            Aluno representante1 = alunoRepository.findById(request.idRepresentante1())
                    .orElseThrow(AlunoNaoExisteException::new);
            representante1.setRepresentante(true);
            alunoRepository.save(representante1);
        }

        conselho.setRepresentante2(alunoRepository.findById(request.idRepresentante2())
                .orElseThrow(RepresentanteNaoExiste::new));

        if (!idsAlunos.contains(conselho.getRepresentante2().getId())) {
            throw new RepresentanteNaoExiste();
        } else {
            Aluno representante2 = alunoRepository.findById(request.idRepresentante2())
                    .orElseThrow(RepresentanteNaoExiste::new);
            representante2.setRepresentante(true);
            alunoRepository.save(representante2);
        }
        conselho.setPedagogico(pedagogicoRepository.findById(request.idPedagogico())
                .orElseThrow(PedagogicoNaoExiste::new));

        conselho.setDataInicio(LocalDate.now());
        conselho.setDataFim(null);
        Conselho salvo = conselhoRepository.save(conselho);

        turma.setIdUltimoConselho(salvo.getId());
        turmaRepository.save(turma);

        return mapper.paraResposta(salvo);
    }


    // BUSCAR TODOS
    public List<ConselhoResponseDTO> listarConselhos() {
        return conselhoRepository.findAll()
                .stream()
                .map(mapper::paraResposta)
                .toList();
    }

    // BUSCAR POR ID
    public ConselhoResponseDTO buscarConselhoPorId(Long id) {
        Conselho conselhoEncontrado = conselhoRepository.findById(id)
                .orElseThrow(ConselhoNaoExiste::new);

        return mapper.paraResposta(conselhoEncontrado);
    }

    // UPDATE
    public ConselhoResponseDTO atualizarConselho(Long id, ConselhoRequestDTO request) {
        Conselho conselhoEncontrado = conselhoRepository.findById(id)
                .orElseThrow(ConselhoNaoExiste::new);

        Conselho conselhoAtualizado = mapper.verificarUpdate(request, conselhoEncontrado);

        return mapper.paraResposta(conselhoRepository.save(conselhoAtualizado));
    }

    // REGRA DE NEGOCIO PARA ATUALIZAR ETAPAS DO CONSELHO
    @Transactional
    public ConselhoResponseDTO atualizarEtapa(Long idConselho, AtualizarEtapaRequestDTO etapaRequest) {

        // VERIFICA SE EXISTE O CONSELHO
        Conselho conselho = conselhoRepository.findById(idConselho)
                .orElseThrow(ConselhoNaoExiste::new);

        // VALIDA SE A ETAPA EXISTE
        if (!EtapasConselho.existeEtapa(etapaRequest.novaEtapa())) {
            throw new EtapaInvalidaException(etapaRequest);
        }

        EtapasConselho novaEtapaEnum = EtapasConselho.valueOf(etapaRequest.novaEtapa().toUpperCase());

        // VALIDA SE O CONSELHO JA ESTÁ NESSA ETAPA
        if (conselho.getEtapas() == novaEtapaEnum) {
            throw new IllegalStateException("O conselho já está nessa etapa.");
        }

        EtapasConselho etapaAnterior = conselho.getEtapas();

        if (etapaAnterior != EtapasConselho.RESULTADO && novaEtapaEnum == EtapasConselho.RESULTADO){
            conselho.setDataFim(LocalDate.now());
        }

        conselho.setEtapas(novaEtapaEnum);
        Conselho conselhoSalvo = conselhoRepository.save(conselho);

        // AQUI QUANDO ETAPA FOR MUDAR PARA PRE CONSELHO ELE VAI FAZER VERIFICAÇÃO E CRIAR PRE CONSELHO
//        if (etapaAnterior != EtapasConselho.PRE_CONSELHO && novaEtapaEnum == EtapasConselho.PRE_CONSELHO) {
//
//            // CRIA O REQUEST PARA O PRE CONSELHO
//            PreConselhoRequestDTO preRequest = new PreConselhoRequestDTO(conselhoSalvo.getId());
//        }

        return mapper.paraResposta(conselhoSalvo);
    }

    // DELETE
    public void deletarConselho(Long id) {
        if (!conselhoRepository.existsById(id)) {
            throw new ConselhoNaoExiste();
        }
        conselhoRepository.deleteById(id);
    }


    public List<ConselhoResponseDTO> filtrarPorEtapa(String etapa) {
        List<ConselhoResponseDTO> conselhos = listarConselhos();

        if (etapa != null && !etapa.isEmpty()) {
            conselhos = conselhos.stream()
                    .filter(conselho -> conselho.etapas().equalsIgnoreCase(etapa))
                    .collect(Collectors.toList());
        }
        if (conselhos.isEmpty()) {
            throw new RuntimeException("Nenhum conselho encontrado para a etapa especificada.");
        }

        return conselhos;
    }

    // listar todos os feedbacks de todos os alunos de um conselho
    public ConselhoFeedbacksResponseDTO listarFeedbacksAlunos(Long id) {
        Conselho conselho = conselhoRepository.findById(id)
                .orElseThrow(ConselhoNaoExiste::new);

        return mapper.paraRespostaFeedbacks(conselho);
    }
  
    // listar conselhos por aluno
    public List<ConselhoResponseDTO> listarConselhosPorAluno(Long idAluno){
        AlunoTurma alunoTurma = alunoTurmaRepository.findByAlunoId(idAluno)
                .orElseThrow(() -> new ConselhoNaoExiste());

        Long idTurma = alunoTurma.getTurma().getId();

        List<Conselho>conselhos = conselhoRepository.findByTurmaId(idTurma);

        if(conselhos.isEmpty()){
            return Collections.emptyList();
        }
        return conselhos.stream()
                .map(conselho -> new ConselhoResponseDTO(
                        conselho.getId(),
                        conselho.getTurma().getId(),
                        conselho.getTurma().getNome(),
                        conselho.getRepresentante1().getId(),
                        conselho.getRepresentante1().getNome(),
                        conselho.getRepresentante2().getId(),
                        conselho.getRepresentante2().getNome(),
                        conselho.getPedagogico().getId(),
                        conselho.getPedagogico().getNome(),
                        conselho.getDataInicio(),
                        conselho.getDataFim(),
                        conselho.getEtapas().toString()
                )).collect(Collectors.toList());
          
      }
  
    public List<ConselhoResponseDTO> listarTodosConselhosDeTurma(Long idTurma) {
        turmaRepository.findById(idTurma)
                .orElseThrow(TurmaNaoExisteException::new);

        List<Conselho> conselhos = conselhoRepository.findByTurmaId(idTurma);

        return conselhos.stream()
                .map(mapper::paraResposta)
                .collect(Collectors.toList());
    }

    public ConselhoResponseDTO buscarConselhoPorTurma(Long idTurma){
        Turma turma  = turmaRepository.findById(idTurma)
                .orElseThrow(TurmaNaoExisteException::new);

        Conselho conselho = conselhoRepository.findById(turma.getIdUltimoConselho())
                .orElseThrow(ConselhoNaoExiste::new);

        return mapper.paraResposta(conselho);
    }
}
