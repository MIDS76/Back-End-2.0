//package com.conselho.api.serviceTesteUnitario;
//import com.conselho.api.dto.mapper.entity.ProfessorMapper;
//import com.conselho.api.dto.request.entity.ProfessorRequestDTO;
//import com.conselho.api.dto.response.entity.ProfessorResponseDTO;
//import com.conselho.api.exception.pedagogico.PedagogicoNaoExiste;
//import com.conselho.api.model.entity.Professor;
//import com.conselho.api.model.usuario.Usuario;
//import com.conselho.api.model.usuario.UsuarioRole;
//import com.conselho.api.repository.entity.ProfessorRepository;
//import com.conselho.api.repository.entity.UsuarioRepository;
//import com.conselho.api.service.entity.ProfessorService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProfessorServiceTest {
//
//    @InjectMocks
//    private ProfessorService profService;
//
//    @Mock
//    private ProfessorRepository profRepository;
//
//    @Mock
//    private UsuarioRepository usuarioRepository;
//
//    @Mock
//    private ProfessorMapper profMapper;
//
//
//    @Test
//    void listarProfessores_DeveRetornarListaDeProfessores() {
//        Usuario usuario1 = new Usuario();
//        Usuario usuario2 = new Usuario(2L, "Vinicius", "vinicius@email.com", "123", UsuarioRole.PROFESSOR);
//
//        when(usuarioRepository.findByRole(UsuarioRole.PROFESSOR))
//                .thenReturn(List.of(usuario1, usuario2));
//
//        List<ProfessorResponseDTO> result = profService.listarProfessores();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("Kristian", result.get(0).nome());
//        assertEquals("Vinicius", result.get(1).nome());
//    }
//
//    @Test
//    void buscarProfessorPorId_DeveLancarExcecao_QuandoProfessorNaoExiste() {
//        Long id = 1L;
//        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            profService.buscarProfessorPorId(id);
//        });
//
//        assertEquals("Professor não encontrado!", exception.getMessage());
//    }
//
//
//    @Test
//    void buscarProfessorPorId_DeveRetornarProfessor_QuandoExiste() {
//        Long id = 1L;
//        Professor professor = new Professor("Vinicius", "vinicius@email.com", "123");
//        professor.setId(id);
//        professor.setRole(UsuarioRole.PROFESSOR);
//
//        ProfessorResponseDTO response = new ProfessorResponseDTO(id, "Vinicius", "vinicius@email.com");
//
//        when(usuarioRepository.findById(id)).thenReturn(Optional.of(professor));
//        when(profMapper.paraRespostaProfessor(professor)).thenReturn(response);
//
//        ProfessorResponseDTO result = profService.buscarProfessorPorId(id);
//
//        assertNotNull(result);
//        assertEquals("Vinicius", result.nome());
//        assertEquals("vinicius@email.com", result.email());
//    }
//
//    @Test
//    void atualizarProfessor_DeveAtualizarComSucesso() {
//        Long id = 1L;
//        ProfessorRequestDTO request = new ProfessorRequestDTO("Carlos", "carlos@gmail.com");
//        Professor professorExistente = new Professor("Antigo", "antigo@gmail.com", "abc");
//        professorExistente.setId(id);
//        professorExistente.setRole(UsuarioRole.PROFESSOR);
//
//        when(profRepository.findById(id)).thenReturn(Optional.of(professorExistente));
//        when(usuarioRepository.findByEmail(request.email())).thenReturn(null);
//
//        Professor professorAtualizado = new Professor("Carlos", "carlos@gmail.com", "123");
//        when(profRepository.save(professorExistente)).thenReturn(professorAtualizado);
//
//        profService.atualizarProfessor(id, request);
//
//        verify(profRepository, times(1)).save(professorExistente);
//    }
//
//    @Test
//    void deletarProfessor_DeveLancarExcecao_QuandoNaoExiste() {
//        Long id = 1L;
//
//        when(usuarioRepository.existsById(id)).thenReturn(false);
//
//        assertThrows(PedagogicoNaoExiste.class, () -> {
//            profService.deletarProfessor(id);
//        });
//
//        verify(usuarioRepository, never()).deleteById(anyLong());
//    }
//    @Test
//    void deletarProfessor_DeveDeletarComSucesso() {
//        Long id = 1L;
//
//        when(usuarioRepository.existsById(id)).thenReturn(true);
//
//        profService.deletarProfessor(id);
//
//        verify(usuarioRepository, times(1)).deleteById(id);
//    }
//
//}