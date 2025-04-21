package com.sinapsis.backend.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.domain.entity.RedeMT;
import com.sinapsis.backend.domain.entity.Subestacao;
import com.sinapsis.backend.domain.mapper.RedeMTMapper;
import com.sinapsis.backend.domain.repository.RedeMTRepository;
import com.sinapsis.backend.domain.repository.SubestacaoRepository;

import jakarta.persistence.EntityNotFoundException;

class RedeMTServiceTest {

    @Mock
    private RedeMTRepository redeMTRepository;

    @Mock
    private SubestacaoRepository subestacaoRepository;

    @Mock
    private RedeMTMapper redeMTMapper;

    @InjectMocks
    private RedeMTService redeMTService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_DeveRetornarRedeMTResponse_QuandoSubestacaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Teste", new BigDecimal("13.8"));
        Subestacao subestacao = new Subestacao();
        RedeMT rede = new RedeMT();
        RedeMTResponse response = new RedeMTResponse(1L, "MT01", "Rede Teste", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(subestacao));
        when(redeMTMapper.toEntity(request, subestacao)).thenReturn(rede);
        when(redeMTRepository.save(rede)).thenReturn(rede);
        when(redeMTMapper.toResponse(rede)).thenReturn(response);

        // Act
        RedeMTResponse result = redeMTService.criar(idSubestacao, request);

        // Assert
        assertEquals(response, result);
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTMapper).toEntity(request, subestacao);
        verify(redeMTRepository).save(rede);
        verify(redeMTMapper).toResponse(rede);
    }

    @Test
    void criar_DeveLancarEntityNotFoundException_QuandoSubestacaoNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Teste", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.criar(idSubestacao, request);
        });

        assertEquals("Subestação não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verifyNoInteractions(redeMTMapper, redeMTRepository);
    }

    @Test
    void listarPorSubestacao_DeveRetornarListaDeRedeMTResponse_QuandoSubestacaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Subestacao subestacao = new Subestacao();
        RedeMT rede = new RedeMT();
        RedeMTResponse response = new RedeMTResponse(1L, "MT01", "Rede Teste", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(subestacao));
        when(redeMTRepository.findAllBySubestacaoId(idSubestacao)).thenReturn(List.of(rede));
        when(redeMTMapper.toResponse(rede)).thenReturn(response);

        // Act
        List<RedeMTResponse> result = redeMTService.listarPorSubestacao(idSubestacao);

        // Assert
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTRepository).findAllBySubestacaoId(idSubestacao);
        verify(redeMTMapper).toResponse(rede);
    }

    @Test
    void listarPorSubestacao_DeveLancarEntityNotFoundException_QuandoSubestacaoNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.listarPorSubestacao(idSubestacao);
        });

        assertEquals("Subestação não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verifyNoInteractions(redeMTRepository, redeMTMapper);
    }

    @Test
    void excluir_DeveChamarDelete_QuandoRedeMTExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;
        Subestacao subestacao = new Subestacao();
        RedeMT rede = new RedeMT();

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(subestacao));
        when(redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)).thenReturn(Optional.of(rede));

        // Act
        redeMTService.excluir(idSubestacao, idRede);

        // Assert
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTRepository).findByIdAndSubestacaoId(idRede, idSubestacao);
        verify(redeMTRepository).delete(rede);
    }

    @Test
    void excluir_DeveLancarEntityNotFoundException_QuandoRedeMTNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(new Subestacao()));
        when(redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.excluir(idSubestacao, idRede);
        });

        assertEquals("RedeMT não encontrada para esta subestação", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTRepository).findByIdAndSubestacaoId(idRede, idSubestacao);
        verify(redeMTRepository, never()).delete(any());
    }

    @Test
    void excluir_DeveLancarEntityNotFoundException_QuandoSubestacaoNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.excluir(idSubestacao, idRede);
        });

        assertEquals("Subestação não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verifyNoInteractions(redeMTRepository);
    }


    @Test
    void atualizar_DeveRetornarRedeMTResponse_QuandoRedeMTExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Atualizada", new BigDecimal("13.8"));
        Subestacao subestacao = new Subestacao();
        RedeMT rede = new RedeMT();
        RedeMTResponse response = new RedeMTResponse(2L, "MT01", "Rede Atualizada", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(subestacao));
        when(redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)).thenReturn(Optional.of(rede));
        when(redeMTRepository.save(rede)).thenReturn(rede);
        when(redeMTMapper.toResponse(rede)).thenReturn(response);

        // Act
        RedeMTResponse result = redeMTService.atualizar(idSubestacao, idRede, request);

        // Assert
        assertEquals(response, result);
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTRepository).findByIdAndSubestacaoId(idRede, idSubestacao);
        verify(redeMTRepository).save(rede);
        verify(redeMTMapper).toResponse(rede);
    }

    @Test
    void atualizar_DeveLancarEntityNotFoundException_QuandoSubestacaoNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Atualizada", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.atualizar(idSubestacao, idRede, request);
        });

        assertEquals("Subestação não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verifyNoInteractions(redeMTRepository, redeMTMapper);
    }

    @Test
    void atualizar_DeveLancarEntityNotFoundException_QuandoRedeMTNaoExiste() {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Atualizada", new BigDecimal("13.8"));

        when(subestacaoRepository.findById(idSubestacao)).thenReturn(Optional.of(new Subestacao()));
        when(redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            redeMTService.atualizar(idSubestacao, idRede, request);
        });

        assertEquals("RedeMT não encontrada para esta subestação", exception.getMessage());
        verify(subestacaoRepository).findById(idSubestacao);
        verify(redeMTRepository).findByIdAndSubestacaoId(idRede, idSubestacao);
        verify(redeMTRepository, never()).save(any());
        verifyNoInteractions(redeMTMapper);
    }
}