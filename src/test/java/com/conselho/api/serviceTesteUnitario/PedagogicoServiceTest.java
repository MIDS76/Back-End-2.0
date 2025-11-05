package com.conselho.api.serviceTesteUnitario;

import com.conselho.api.dto.mapper.PedagogicoMapper;
import com.conselho.api.dto.request.PedagogicoRequestDTO;
import com.conselho.api.dto.response.PedagogicoResponseDTO;
import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
import com.conselho.api.model.Pedagogico;
import com.conselho.api.model.usuario.Usuario;
import com.conselho.api.model.usuario.UsuarioRole;
import com.conselho.api.repository.PedagogicoRepository;
import com.conselho.api.repository.UsuarioRepository;
import com.conselho.api.service.PedagogicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedagogicoServiceTest {

    @InjectMocks
    private PedagogicoService service;

    @Mock
    private PedagogicoRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PedagogicoMapper mapper;

    @Test
    void listarPedagogicos_DeveRetornarListaDePedagogico() {
        Usuario user1 = new Usuario(1L, "Jusci", "jusci@gmail.com", "123", UsuarioRole.PEDAGOGICO);
        Usuario user2 = new Usuario(2L, "Maria", "maria@gmail.com", "123", UsuarioRole.PEDAGOGICO);

        when(usuarioRepository.findByRole(UsuarioRole.PEDAGOGICO))
                .thenReturn(List.of(user1, user2));

        List<PedagogicoResponseDTO> result = service.listarPedagogico();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jusci", result.get(0).nome());
        assertEquals("Maria", result.get(1).nome());
    }

    @Test
    void buscarPedagogicoPorId_DeveLancarExcecao_QuandoPedagogicoNaoExiste() {
        Long id = 1L;

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPedagogicoPorId(id);
        });

        assertEquals("Pedagogico não encontrado!", exception.getMessage());
    }

    @Test
    void buscarPedagogicoPorId_DeveRetornarPedagogico_QuandoExiste() {
        Long id = 1L;
        Pedagogico pedagogico = new Pedagogico("Maria", "maria@gmail.com", "123");
        pedagogico.setId(id);

        PedagogicoResponseDTO responseDTO = new PedagogicoResponseDTO(id, "Maria", "maria@gmail.com");

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(pedagogico));
        when(mapper.paraResposta(pedagogico))
                .thenReturn(responseDTO);

        PedagogicoResponseDTO result = service.buscarPedagogicoPorId(id);
        assertNotNull(result);
        assertEquals("Maria", result.nome());
        assertEquals("maria@gmail.com", result.email());
    }

    @Test
    void atualizarPedagogico_DeveAtualizarComSucesso() {
        Long id = 1L;

        PedagogicoRequestDTO request = new PedagogicoRequestDTO("Maria", "maria@gmail.com", "123");

        Pedagogico pedagogicoExiste = new Pedagogico("Maria", "maria@gmail.com", "123");
        pedagogicoExiste.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(pedagogicoExiste));

        lenient().when(usuarioRepository.findByEmail(request.email())).thenReturn(null);

        Pedagogico pedagogicoAtualizado = new Pedagogico("Maria", "maria@gmail.com", "123");
        when(repository.save(pedagogicoExiste)).thenReturn(pedagogicoAtualizado);

        service.atualizarPedagogico(id, request);

        verify(repository, times(1)).save(pedagogicoExiste);
    }

    @Test
    void deletarPedagogico_DeveLancarExcecao_QuandoNaoExiste() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(PedagogicoNaoExiste.class, () -> {
            service.deletarPedagogico(id);
        });

        verify(usuarioRepository, never()).deleteById(anyLong());
    }

    @Test
    void deletarPedagogico_DeveDeletarComSucesso() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(true);

        service.deletarPedagogico(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
