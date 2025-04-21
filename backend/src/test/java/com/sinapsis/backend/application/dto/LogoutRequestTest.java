package com.sinapsis.backend.application.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LogoutRequestTest {

    @Test
    void isValid_DeveRetornarTrue_QuandoTokenValido() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest("valid-token");

        // Act
        boolean result = logoutRequest.isValid();

        // Assert
        assertTrue(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoTokenForNulo() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest(null);

        // Act
        boolean result = logoutRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoTokenForVazio() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest("");

        // Act
        boolean result = logoutRequest.isValid();

        // Assert
        assertFalse(result);
    }

    @Test
    void isValid_DeveRetornarFalse_QuandoTokenForEspacosEmBranco() {
        // Arrange
        LogoutRequest logoutRequest = new LogoutRequest("   ");

        // Act
        boolean result = logoutRequest.isValid();

        // Assert
        assertFalse(result);
    }
}
