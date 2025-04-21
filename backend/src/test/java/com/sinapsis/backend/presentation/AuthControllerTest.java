package com.sinapsis.backend.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinapsis.backend.application.dto.AuthRequest;
import com.sinapsis.backend.application.dto.AuthResponse;
import com.sinapsis.backend.application.service.AuthService;

import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void login_DeveRetornarAuthResponse_QuandoCredenciaisValidas() throws Exception {
        // Arrange
        AuthRequest request = new AuthRequest("testuser", "password");
        AuthResponse response = new AuthResponse("mocked-jwt-token");

        when(authService.login(any(AuthRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

        verify(authService).login(any(AuthRequest.class));
    }

    @Test
    void login_DeveRetornarBadRequest_QuandoRequestInvalido() throws Exception {
        // Arrange
        String invalidRequest = "{}"; // Request sem os campos obrigatórios

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void logout_DeveRetornarNoContent_QuandoTokenValido() throws Exception {
        // Arrange
        String token = "mocked-jwt-token";
        String logoutBody = String.format("{ \"token\": \"%s\" }", token);

        // Act & Assert
        mockMvc.perform(post("/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logoutBody))
                .andExpect(status().isNoContent());

        verify(authService).logout(token);
    }

    @Test
    void logout_DeveRetornarBadRequest_QuandoTokenNaoForEnviado() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        verify(authService, never()).logout(anyString());
    }

    @Test
    void logout_DeveRetornarBadRequest_QuandoTokenEstaVazio() throws Exception {
        // Arrange
        String logoutBody = "{ \"token\": \"\" }"; // Token vazio

        // Act & Assert
        mockMvc.perform(post("/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logoutBody))
                .andExpect(status().isBadRequest());

        verify(authService, never()).logout(anyString());
    }

    @Test
    void logout_DeveRetornarBadRequest_QuandoTokenEstaEmBranco() throws Exception {
        // Arrange
        String logoutBody = "{ \"token\": \"   \" }"; // Token em branco

        // Act & Assert
        mockMvc.perform(post("/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(logoutBody))
                .andExpect(status().isBadRequest());

        verify(authService, never()).logout(anyString());
    }

    
}
