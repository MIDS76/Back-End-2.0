package com.conselho.api.serviceTestes.entity;

import com.conselho.api.dto.mapper.entity.WegMapper;
import com.conselho.api.dto.request.entity.WegRequestDTO;
import com.conselho.api.dto.response.entity.WegResponseDTO;
import com.conselho.api.exception.weg.WegNaoExisteException;
import com.conselho.api.model.entity.Weg;
import com.conselho.api.model.usuario.UsuarioRole;

import com.conselho.api.repository.entity.UsuarioRepository;
import com.conselho.api.repository.entity.WegRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WegServiceTest {
    @InjectMocks
    private  WegService service;

    @Mock
    private WegRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private WegMapper mapper;

    @Test
    void deveBuscarTodosOsUsuariosWeg() {
        Weg w1 = new Weg("hellen", "hellen@gmail", "123");
        w1.setId(1L);
        w1.setRole(UsuarioRole.WEG);

        Weg w2 = new Weg("vitor", "vitor@gmail.com", "142");
        w2.setId(2L);
        w2.setRole(UsuarioRole.WEG);

        when(usuarioRepository.findByRole(UsuarioRole.WEG))
                .thenReturn(List.of(w1, w2));

        List<WegResponseDTO>result = service.buscarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("hellen", result.get(0).nome());
        assertEquals("vitor", result.get(1).nome());
    }

    @Test
    void buscarUsuarioWegPorId_DeveLancarExcecao_QuandoWegNaoExistit(){
        Long id = 1L;
        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->{
            service.buscarPorId(id);
        });

        assertEquals("Usuário weg não encontrado.", exception.getMessage());

        Mockito.verify(usuarioRepository, times(1)).findById(id);
    }
    @Test
    void buscarUsuarioWegPorId_DeveLancarExcecao_QuandoWegExistit(){
        Long id = 1L;

        Weg w1 = new Weg("Vinicius", "vini@gmai.com", "1234");
        w1.setId(id);

        WegResponseDTO responseDTO = new WegResponseDTO(id, "Vinicius", "vini@gmail.com");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(w1));
        when(mapper.paraResposta(w1))
                .thenReturn(responseDTO);

        WegResponseDTO result = service.buscarPorId(id);
        assertNotNull(result);
        assertEquals("Vinicius", result.nome());
        assertEquals("vini@gmail.com", result.email());
    }

    @Test
    void updateUsuarioWeg_DeveRetornarAtualizaromSucesso() {
        Long id = 1L;

        WegRequestDTO requestDTO = new WegRequestDTO("Vinicius", "vinicius@gmail.com");

        Weg wegExistente = new Weg("Vinicius", "vini@gmail.com", "12345");
        wegExistente.setId(id);

        Weg wegAtualizado = new Weg("Vinicius", "vinicius@gmail.com", "12345");

        WegResponseDTO responseDTO = new WegResponseDTO(1L, "vinicius@gmail.com", "vinicius@gmail.com");

        when(repository.findById(id)).thenReturn(Optional.of(wegExistente));
        when(usuarioRepository.findByEmail(requestDTO.email())).thenReturn(null);
        when (mapper.paraUpdate(requestDTO, wegExistente)).thenReturn(wegAtualizado);
        when(repository.save(wegAtualizado)).thenReturn(wegAtualizado);
        when(mapper.paraResposta(wegAtualizado)).thenReturn(responseDTO);

        WegResponseDTO result = service.update(id, requestDTO);

        assertEquals(responseDTO, result);

        verify(repository).save(wegAtualizado);
    }

    @Test
    void deletarUsuarioWeg_DeveDeletarComSucesso() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        service.delete(1L);

        verify(usuarioRepository).deleteById(1L);
    }
    @Test
    void deletarUsuarioWeg_DeveLancarExcecao_QuandoNaoExiste() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(false);


        assertThrows(WegNaoExisteException.class, () ->{
            service.delete(id);
        });

        verify(usuarioRepository, never()).deleteById(anyLong());
    }


}