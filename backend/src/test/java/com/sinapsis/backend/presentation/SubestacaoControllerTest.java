package com.sinapsis.backend.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.application.service.SubestacaoService;
import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;
import com.sinapsis.backend.infrastructure.config.JwtService;

import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class SubestacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private SubestacaoService subestacaoService;

    @MockBean
    private UsuarioRepository usuarioRepository;



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
    void listar_DeveRetornarListaDeSubestacoes() throws Exception {
        // Arrange
        SubestacaoResponse response = new SubestacaoResponse(1L, "SUB01", "Subestação A", -23.5, -46.6, new ArrayList<>());
        when(subestacaoService.listarTodos()).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(get("/api/subestacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].codigo").value("SUB01"))
                .andExpect(jsonPath("$[0].nome").value("Subestação A"))
                .andExpect(jsonPath("$[0].latitude").value(-23.5))
                .andExpect(jsonPath("$[0].longitude").value(-46.6));

        verify(subestacaoService).listarTodos();
    }

    @Test
    void buscarPorId_DeveRetornarSubestacao_QuandoIdValido() throws Exception {
        // Arrange
        SubestacaoResponse response = new SubestacaoResponse(1L, "SUB01", "Subestação A", -23.5, -46.6, new ArrayList<>());
        when(subestacaoService.buscarPorId(1L)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/subestacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.codigo").value("SUB01"))
                .andExpect(jsonPath("$.nome").value("Subestação A"))
                .andExpect(jsonPath("$.latitude").value(-23.5))
                .andExpect(jsonPath("$.longitude").value(-46.6));

        verify(subestacaoService).buscarPorId(1L);
    }

    @Test
    void criar_DeveRetornarSubestacaoCriada_QuandoRequestValido() throws Exception {
        // Arrange
        SubestacaoRequest request = new SubestacaoRequest("SUB01", "Subestação A", -23.5, -46.6, new ArrayList<>());
        SubestacaoResponse response = new SubestacaoResponse(1L, "SUB01", "Subestação A", -23.5, -46.6, new ArrayList<>());

        when(subestacaoService.criar(any(SubestacaoRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/subestacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken())
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.codigo").value("SUB01"))
                .andExpect(jsonPath("$.nome").value("Subestação A"))
                .andExpect(jsonPath("$.latitude").value(-23.5))
                .andExpect(jsonPath("$.longitude").value(-46.6));

        verify(subestacaoService).criar(any(SubestacaoRequest.class));
    }

    @Test
    void atualizar_DeveRetornarSubestacaoAtualizada_QuandoRequestValido() throws Exception {
        // Arrange
        SubestacaoRequest request = new SubestacaoRequest("SUB02", "Subestação B", -22.0, -45.0, new ArrayList<>());
        SubestacaoResponse response = new SubestacaoResponse(1L, "SUB02", "Subestação B", -22.0, -45.0, new ArrayList<>());

        when(subestacaoService.atualizar(eq(1L), any(SubestacaoRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/subestacoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken())
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.codigo").value("SUB02"))
                .andExpect(jsonPath("$.nome").value("Subestação B"))
                .andExpect(jsonPath("$.latitude").value(-22.0))
                .andExpect(jsonPath("$.longitude").value(-45.0));

        verify(subestacaoService).atualizar(eq(1L), any(SubestacaoRequest.class));
    }

    @Test
    void excluir_DeveRetornarNoContent_QuandoIdValido() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/subestacoes/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(subestacaoService).excluir(1L);
    }
}