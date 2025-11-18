package com.conselho.api.serviceTestes;

import com.conselho.api.dto.mapper.TurmaMapper;
import com.conselho.api.dto.request.TurmaRequestDTO;
import com.conselho.api.dto.response.TurmaResponseDTO;
import com.conselho.api.exception.turma.TurmaNaoExisteException;
import com.conselho.api.model.Turma;
import com.conselho.api.repository.TurmaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Turma turma = repository.findById(idTurma)
                .orElseThrow(TurmaNaoExisteException::new);

        return mapper.paraResposta(turma);
    }

    public List<TurmaResponseDTO> ordenarTurmaOrdemAlfabetica(String ordem){
        List<TurmaResponseDTO> turmas = listarTurmas();
        List<TurmaResponseDTO> turmasModificavel = new ArrayList<>(turmas);
        Comparator<TurmaResponseDTO> comparator = Comparator.comparing(TurmaResponseDTO::nome);

        if ("Z-A".equalsIgnoreCase(ordem)) {
            turmasModificavel.sort(comparator.reversed());
        } else {
            turmasModificavel.sort(comparator);
        }

        return turmasModificavel;
    }

    public List<TurmaResponseDTO> filtrarPorCurso(String curso) {
        List<TurmaResponseDTO> turmas = listarTurmas();
        List<String> cursos = listarCurso();

        if (!curso.isEmpty() && cursos.contains(curso)) {
            turmas = turmas.stream()
                    .filter(turma -> turma.curso().equalsIgnoreCase(curso))
                    .collect(Collectors.toList());
        }

        return turmas;
    }

    public List<TurmaResponseDTO> filtrarPorAno(Long anoEntrada){
        List<TurmaResponseDTO> turmas = listarTurmas();
        List<Integer> anosEntrada = listarAnosDeEntrada();

        if(anoEntrada != 0 && anosEntrada.contains(anoEntrada.intValue())){
            turmas = turmas.stream()
                    .filter(turma -> turma.dataInicio().getYear() == anoEntrada)
                    .collect(Collectors.toList());
        }
        return  turmas;
    }

    public List<Integer> listarAnosDeEntrada(){
        List<Turma> turmas = repository.findAll();
        Set<Integer> anosDeEntrada = new HashSet<>();
        for (Turma t : turmas) {
            if (t.getDataInicio() != null) {
                anosDeEntrada.add(t.getDataInicio().getYear());
            }
        }
        return new ArrayList<>(anosDeEntrada);
    }

    public List<String> listarCurso(){
        List<Turma> turmas = repository.findAll();
        Set<String> curso = new HashSet<>();
        for (Turma t : turmas) {
            if (t.getCurso() != null) {
                curso.add(t.getCurso());
            }
        }
        return new ArrayList<>(curso);
    }

    public TurmaResponseDTO atualizarTurma(Long idTurma, TurmaRequestDTO request){
        Turma turma = repository.findById(idTurma)
                .orElseThrow(TurmaNaoExisteException::new);

        Turma newTurma = mapper.paraUpdate(request, turma);
        return mapper.paraResposta(repository.save(newTurma));
    }


    public void deletarTurma(Long idTurma) {
        repository.findById(idTurma)
                .orElseThrow(TurmaNaoExisteException::new);

        repository.deleteById(idTurma);
    }
}