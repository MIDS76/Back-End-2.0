package com.conselho.api.service;

import com.conselho.api.dto.mapper.ConselhoMapper;
import com.conselho.api.dto.request.AtualizarEtapaRequestDTO;
import com.conselho.api.dto.request.ConselhoRequestDTO;
import com.conselho.api.dto.request.PreConselhoRequest;
import com.conselho.api.dto.response.ConselhoResponseDTO;
import com.conselho.api.exception.conselho.ConselhoNaoExiste;
import com.conselho.api.exception.conselho.EtapaInvalidaException;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.exception.representante.RepresentanteNaoExiste;
import com.conselho.api.exception.turma.TurmaNaoExiste;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.conselho.EtapasConselho;
import com.conselho.api.repository.AlunoRepository;
import com.conselho.api.repository.ConselhoRepository;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ConselhoService {
    private ConselhoMapper mapper;
    private ConselhoRepository conselhoRepository;
    private TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;
    private PedagogicoRepository pedagogicoRepository;
    private PreConselhoService preConselhoService;

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

    // REGRA DE NEGOCIO PARA ATUALIZAR ETAPAS DO CONSELHO
    @Transactional
    public ConselhoResponseDTO atualizarEtapa(Long idConselho, String novaEtapa, LocalDate dataInicioPre, LocalDate dataFimPre) {

        // VERIFICA SE EXISTE O CONSELHO
        Conselho conselho = conselhoRepository.findById(idConselho)
                .orElseThrow(ConselhoNaoExiste::new);

        // VALIDA SE A ETAPA EXISTE
        if (!EtapasConselho.existeEtapa(novaEtapa)) {
            throw new EtapaInvalidaException(novaEtapa);
        }

        EtapasConselho novaEtapaEnum = EtapasConselho.valueOf(novaEtapa.toUpperCase());

        // VALIDA SE O CONSELHO JA ESTÁ NESSA ETAPA
        if (conselho.getEtapas() == novaEtapaEnum) {
            throw new IllegalStateException("O conselho já está nessa etapa.");
        }

        EtapasConselho etapaAnterior = conselho.getEtapas();

        conselho.setEtapas(novaEtapaEnum);
        Conselho conselhoSalvo = conselhoRepository.save(conselho);

        if (etapaAnterior != EtapasConselho.PRE_CONSELHO && novaEtapaEnum == EtapasConselho.PRE_CONSELHO) {

            // CRIA O REQUEST PARA O PRE CONSELHO
            PreConselhoRequest preRequest = new PreConselhoRequest(conselhoSalvo.getId(), dataInicioPre, dataFimPre);

            preConselhoService.criarPreConselhoAutomatico(preRequest);
        }

        return mapper.paraResposta(conselhoSalvo);
    }

    // DELETE
    public void deletarConselho(Long id){
        if (!conselhoRepository.existsById(id)){
            throw new ConselhoNaoExiste();
        }
        conselhoRepository.deleteById(id);
    }
}
