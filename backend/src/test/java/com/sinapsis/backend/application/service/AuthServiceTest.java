package com.sinapsis.backend.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sinapsis.backend.application.dto.AuthRequest;
import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;
import com.sinapsis.backend.infrastructure.config.JwtService;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsAreValid() {
        // Arrange
        var username = "testuser";
        var password = "testpassword";
        var jwtToken = "mocked-jwt-token";

        var authRequest = new AuthRequest(username, password);
        var usuario = new Usuario();
        usuario.setUsername(username);
        String encodedPassword = "encoded-testpassword";
        usuario.setPassword(encodedPassword);

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(any(), any())).thenReturn(true); // Simula a codificação da senha
        when(jwtService.generateToken(usuario)).thenReturn(jwtToken);

        // Act
        var response = authService.login(authRequest);

        // Assert
        assertEquals(jwtToken, response.token());
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByUsername(username);
        verify(jwtService).generateToken(usuario);
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        var username = "nonexistentuser";
        var password = "testpassword";

        var authRequest = new AuthRequest(username, password);

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(authRequest));

        assertEquals("No value present", exception.getMessage());
        verify(usuarioRepository).findByUsername(username);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void logout_ShouldInvalidateToken_WhenTokenIsValid() {
        // Arrange
        var token = "valid-jwt-token";

        // Act
        authService.logout(token);

        // Assert
        verify(jwtService).invalidateToken(token);
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsIncorrect() {
        // Arrange
        var username = "testuser";
        var password = "wrongpassword";

        var authRequest = new AuthRequest(username, password);
        var usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword("correctpassword");

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        doThrow(new RuntimeException("Bad credentials")).when(authManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // authService.login(authRequest);
        // Act & Assert
        var exception = assertThrows(
                RuntimeException.class,
                () -> authService.login(authRequest));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(usuarioRepository).findByUsername(username);
        verify(jwtService, never()).generateToken(any());
    }
}
