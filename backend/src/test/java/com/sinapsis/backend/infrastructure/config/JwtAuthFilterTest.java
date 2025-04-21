package com.sinapsis.backend.infrastructure.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_DeveContinuarFiltro_QuandoAuthHeaderNaoPresente() throws ServletException, IOException {
        // Arrange
        request.setServletPath("/api/test");

        // Act
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_DeveLancarExcecao_QuandoTokenInvalido() throws ServletException, IOException {
        // Arrange
        request.addHeader("Authorization", "Bearer invalid-token");
        when(jwtService.isTokenValid("invalid-token")).thenReturn(false);

        // Act & Assert
        assertThrows(SecurityException.class, () -> {
            jwtAuthFilter.doFilterInternal(request, response, filterChain);
        });

        verify(jwtService).isTokenValid("invalid-token");
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_DeveAutenticarUsuario_QuandoTokenValido() throws ServletException, IOException {
        // Arrange
        String token = "valid-token";
        String username = "testuser";
        Usuario user = Usuario.builder()
            .username(username)
            .password("password")
            .build();

        request.addHeader("Authorization", "Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(jwtService.isTokenValid(token)).thenReturn(true);
        when(usuarioRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        when(jwtService.isValid(token, user)).thenReturn(true);

        // Act
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).extractUsername(token);
        verify(jwtService).isTokenValid(token);
        verify(usuarioRepository).findByUsername(username);
        verify(jwtService).isValid(token, user);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(user, authentication.getPrincipal());
        verify(filterChain).doFilter(request, response);
    }

    
}