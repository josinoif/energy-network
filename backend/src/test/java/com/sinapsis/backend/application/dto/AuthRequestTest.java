package com.sinapsis.backend.application.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthRequestTest {

    @Test
    void isValid_DeveRetornarTrue_QuandoUsernameEPasswordValidos() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("validUsername", "validPassword");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertTrue(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoUsernameForNulo() {
        // Arrange
        AuthRequest authRequest = new AuthRequest(null, "validPassword");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoPasswordForNulo() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("validUsername", null);

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoUsernameForVazio() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("", "validPassword");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoPasswordForVazio() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("validUsername", "");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoUsernameForEspacosEmBranco() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("   ", "validPassword");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoPasswordForEspacosEmBranco() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("validUsername", "   ");

        // Act
        boolean result = authRequest.isValid();

        // Assert
        assertFalse(result);
    }
}
