package com.sinapsis.backend.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.application.service.SubestacaoService;
import com.sinapsis.backend.domain.entity.Subestacao;
import com.sinapsis.backend.domain.mapper.SubestacaoMapper;
import com.sinapsis.backend.domain.repository.SubestacaoRepository;

import jakarta.persistence.EntityNotFoundException;

class SubestacaoServiceTest {

    @Mock
    private SubestacaoRepository subestacaoRepository;

    @Mock
    private SubestacaoMapper subestacaoMapper;

    @InjectMocks
    private SubestacaoService subestacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodos_DeveRetornarListaDeSubestacaoResponse() {
        // Arrange
        Subestacao subestacao = new Subestacao();
        SubestacaoResponse response = new SubestacaoResponse(1L, "SB1" ,"Subestação A", -23.5, -46.6, new ArrayList<>());

        when(subestacaoRepository.findAll()).thenReturn(List.of(subestacao));
        when(subestacaoMapper.toResponse(subestacao)).thenReturn(response);

        // Act
        List<SubestacaoResponse> result = subestacaoService.listarTodos();

        // Assert
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(subestacaoRepository).findAll();
        verify(subestacaoMapper).toResponse(subestacao);
    }

    @Test
    void buscarPorId_DeveRetornarSubestacaoResponse_QuandoIdExiste() {
        // Arrange
        Long id = 1L;
        Subestacao subestacao = new Subestacao();
        SubestacaoResponse response = new SubestacaoResponse(1L, "SB1" ,"Subestação A", -23.5, -46.6, new ArrayList<>());

        when(subestacaoRepository.findById(id)).thenReturn(Optional.of(subestacao));
        when(subestacaoMapper.toResponse(subestacao)).thenReturn(response);

        // Act
        SubestacaoResponse result = subestacaoService.buscarPorId(id);

        // Assert
        assertEquals(response, result);
        verify(subestacaoRepository).findById(id);
        verify(subestacaoMapper).toResponse(subestacao);
    }

    @Test
    void buscarPorId_DeveLancarEntityNotFoundException_QuandoIdNaoExiste() {
        // Arrange
        Long id = 1L;

        when(subestacaoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            subestacaoService.buscarPorId(id);
        });

        assertEquals("Subestação com id=" + id + " não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(id);
        verifyNoInteractions(subestacaoMapper);
    }

    @Test
    void excluir_DeveChamarDeleteById_QuandoIdExiste() {
        // Arrange
        Long id = 1L;

        when(subestacaoRepository.existsById(id)).thenReturn(true);

        // Act
        subestacaoService.excluir(id);

        // Assert
        verify(subestacaoRepository).existsById(id);
        verify(subestacaoRepository).deleteById(id);
    }

    @Test
    void excluir_DeveLancarEntityNotFoundException_QuandoIdNaoExiste() {
        // Arrange
        Long id = 1L;

        when(subestacaoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            subestacaoService.excluir(id);
        });

        assertEquals("Subestação com id=" + id + " não encontrada", exception.getMessage());
        verify(subestacaoRepository).existsById(id);
        verify(subestacaoRepository, never()).deleteById(id);
    }

    @Test
    void criar_DeveRetornarSubestacaoResponse() {
        // Arrange
        SubestacaoRequest request = new SubestacaoRequest("SB1" ,"Subestação A", -23.5, -46.6, new ArrayList<>());
        Subestacao subestacao = new Subestacao();
        Subestacao salva = new Subestacao();
        SubestacaoResponse response = new SubestacaoResponse(1L, "SB1" ,"Subestação A", -23.5, -46.6, new ArrayList<>());

        when(subestacaoMapper.toEntity(request)).thenReturn(subestacao);
        when(subestacaoRepository.save(subestacao)).thenReturn(salva);
        when(subestacaoMapper.toResponse(salva)).thenReturn(response);

        // Act
        SubestacaoResponse result = subestacaoService.criar(request);

        // Assert
        assertEquals(response, result);
        verify(subestacaoMapper).toEntity(request);
        verify(subestacaoRepository).save(subestacao);
        verify(subestacaoMapper).toResponse(salva);
    }

    @Test
    void atualizar_DeveRetornarSubestacaoResponse_QuandoIdExiste() {
        // Arrange
        Long id = 1L;
        SubestacaoRequest request = new SubestacaoRequest("SB1" ,"Subestação Atualizada", -23.5, -46.6, new ArrayList<>());
        Subestacao subestacao = new Subestacao();
        Subestacao atualizada = new Subestacao();
        SubestacaoResponse response = new SubestacaoResponse(1L, "SB1" ,"Subestação Atualizada", -23.5, -46.6, new ArrayList<>());

        when(subestacaoRepository.findById(id)).thenReturn(Optional.of(subestacao));
        // when(subestacaoMapper.updateFromRequest(request, subestacao)).thenReturn(subestacao);

        when(subestacaoRepository.findById(id)).thenReturn(Optional.of(subestacao));
        when(subestacaoRepository.save(subestacao)).thenReturn(atualizada);
        when(subestacaoMapper.toResponse(atualizada)).thenReturn(response);

        // Act
        SubestacaoResponse result = subestacaoService.atualizar(id, request);

        // Assert
        assertEquals(response, result);
        verify(subestacaoRepository).findById(id);
        verify(subestacaoMapper).updateFromRequest(request, subestacao);
        verify(subestacaoRepository).save(subestacao);
        verify(subestacaoMapper).toResponse(atualizada);
    }

    @Test
    void atualizar_DeveLancarEntityNotFoundException_QuandoIdNaoExiste() {
        // Arrange
        Long id = 1L;
        SubestacaoRequest request = new SubestacaoRequest("SB1" ,"Subestação Atualizada", -23.5, -46.6, new ArrayList<>());

        when(subestacaoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            subestacaoService.atualizar(id, request);
        });

        assertEquals("Subestação não encontrada", exception.getMessage());
        verify(subestacaoRepository).findById(id);
        verifyNoInteractions(subestacaoMapper);
    }
}
