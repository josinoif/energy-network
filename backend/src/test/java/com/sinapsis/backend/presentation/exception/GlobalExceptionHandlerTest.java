package com.sinapsis.backend.presentation.exception;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sinapsis.backend.application.service.SubestacaoService;
import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;
import com.sinapsis.backend.infrastructure.config.JwtService;

import jakarta.persistence.EntityNotFoundException;


@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private SubestacaoService subestacaoService;

    private String generateToken() {

        Usuario usuario = Usuario.builder()
        .username("testuser")
        .password("testpassword")
        .build();

        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.of(usuario));
        
        String token = jwtService.generateToken(usuario);
        return token;
    }

    @Test
    void handleEntityNotFoundException_DeveRetornarNotFound() throws Exception {
    
        // Simula uma requisição que lança EntityNotFoundException
        when(subestacaoService.buscarPorId(999L)).thenThrow(new EntityNotFoundException("Subestação com id=999 não encontrada"));
        // Simula uma requisição que lança EntityNotFoundException
        mockMvc.perform(get("/api/subestacoes/999")
        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Subestação com id=999 não encontrada"));
    }

    @Test
    void handleUnexpectedException_DeveRetornarInternalServerError() throws Exception {
        // Simula uma requisição que lança uma exceção inesperada
        when(subestacaoService.buscarPorId(999L)).thenThrow(new RuntimeException("Erro inesperado"));
        // Simula uma requisição que lança uma exceção genérica
        mockMvc.perform(get("/api/subestacoes/999")
        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Ocorreu um erro inesperado"));
    }

    @Test
    void handleHttpRequestMethodNotSupportedException_DeveRetornarNotFound() throws Exception {
        // Simula uma requisição com método HTTP não suportado
        mockMvc.perform(patch("/api/subestacoes/999")
        .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("METHOD_NOT_SUPPORTED"))
                .andExpect(jsonPath("$.message").value("O recurso solicitado não suporta o método HTTP utilizado."));
    }
}