package com.sinapsis.backend.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.application.service.RedeMTService;
import com.sinapsis.backend.application.service.SubestacaoService;
import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;
import com.sinapsis.backend.infrastructure.config.JwtService;

import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class RedeMTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RedeMTService redeMTService;

    @MockBean
    private SubestacaoService subestacaoService;

    @Autowired
    private JwtService jwtService;

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
    void criar_DeveRetornarRedeMTCriada_QuandoRequestValido() throws Exception {
        // Arrange
        Long idSubestacao = 1L;
        RedeMTRequest request = new RedeMTRequest(null, "MT01", "Rede Teste", new BigDecimal("13.8"));
        RedeMTResponse response = new RedeMTResponse(1L, "MT01", "Rede Teste", new BigDecimal("13.8"));


        when(subestacaoService.buscarPorId(idSubestacao)).thenReturn(new SubestacaoResponse(idSubestacao, null, null, null, null, null));

        when(redeMTService.criar(eq(idSubestacao), any(RedeMTRequest.class))).thenReturn(response);


        // Act & Assert
        mockMvc.perform(post("/api/subestacoes/1/redesmt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization", "Bearer " + generateToken()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.codigo").value("MT01"))
                .andExpect(jsonPath("$.nome").value("Rede Teste"))
                .andExpect(jsonPath("$.tensaoNominal").value(13.8));

        verify(redeMTService).criar(eq(idSubestacao), any(RedeMTRequest.class));
    }

    @Test
    void listar_DeveRetornarListaDeRedesMT_QuandoIdSubestacaoValido() throws Exception {
        // Arrange
        Long idSubestacao = 1L;
        RedeMTResponse response = new RedeMTResponse(1L, "MT01", "Rede Teste", new BigDecimal("13.8"));

        when(redeMTService.listarPorSubestacao(idSubestacao)).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(get("/api/subestacoes/1/redesmt")
        .header("Authorization", "Bearer " + generateToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].codigo").value("MT01"))
                .andExpect(jsonPath("$[0].nome").value("Rede Teste"))
                .andExpect(jsonPath("$[0].tensaoNominal").value(13.8));

        verify(redeMTService).listarPorSubestacao(idSubestacao);
    }

    @Test
    void atualizar_DeveRetornarRedeMTAtualizada_QuandoRequestValido() throws Exception {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;
        RedeMTRequest request = new RedeMTRequest(idRede,"MT02", "Rede Atualizada", new BigDecimal("15.0"));
        RedeMTResponse response = new RedeMTResponse(2L, "MT02", "Rede Atualizada", new BigDecimal("15.0"));

        when(redeMTService.atualizar(eq(idSubestacao), eq(idRede), any(RedeMTRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/subestacoes/1/redesmt/2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + generateToken())
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.codigo").value("MT02"))
                .andExpect(jsonPath("$.nome").value("Rede Atualizada"))
                .andExpect(jsonPath("$.tensaoNominal").value(15.0));

        verify(redeMTService).atualizar(eq(idSubestacao), eq(idRede), any(RedeMTRequest.class));
    }

    @Test
    void excluir_DeveRetornarNoContent_QuandoIdValido() throws Exception {
        // Arrange
        Long idSubestacao = 1L;
        Long idRede = 2L;

        // Act & Assert
        mockMvc.perform(delete("/api/subestacoes/1/redesmt/2")
        .header("Authorization", "Bearer " + generateToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(redeMTService).excluir(idSubestacao, idRede);
    }
}