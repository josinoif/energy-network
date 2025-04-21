package com.sinapsis.backend.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.domain.entity.Subestacao;

class SubestacaoMapperTest {

    private SubestacaoMapper subestacaoMapper;

    @BeforeEach
    void setUp() {
        subestacaoMapper = new SubestacaoMapper();
    }

    @Test
    void toResponse_DeveRetornarSubestacaoResponse_QuandoDadosValidos() {
        // Arrange
        Subestacao subestacao = Subestacao.builder()
            .id(1L)
            .codigo("SUB01")
            .nome("Subestação A")
            .latitude(new BigDecimal("-23.5"))
            .longitude(new BigDecimal("-46.6"))
            .build();

        // Act
        SubestacaoResponse result = subestacaoMapper.toResponse(subestacao);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("SUB01", result.codigo());
        assertEquals("Subestação A", result.nome());
        assertEquals(-23.5, result.latitude());
        assertEquals(-46.6, result.longitude());
    }

    @Test
    void toEntity_DeveRetornarSubestacao_QuandoDadosValidos() {
        // Arrange
        SubestacaoRequest request = new SubestacaoRequest("SUB01", "Subestação A", -23.5, -46.6);

        // Act
        Subestacao result = subestacaoMapper.toEntity(request);

        // Assert
        assertNotNull(result);
        assertEquals("SUB01", result.getCodigo());
        assertEquals("Subestação A", result.getNome());
        assertEquals(new BigDecimal("-23.5"), result.getLatitude());
        assertEquals(new BigDecimal("-46.6"), result.getLongitude());
    }

    @Test
    void updateFromRequest_DeveAtualizarSubestacao_QuandoDadosValidos() {
        // Arrange
        SubestacaoRequest request = new SubestacaoRequest("SUB02", "Subestação B", -22.0, -45.0);
        Subestacao subestacao = Subestacao.builder()
            .id(1L)
            .codigo("SUB01")
            .nome("Subestação A")
            .latitude(new BigDecimal("-23.5"))
            .longitude(new BigDecimal("-46.6"))
            .build();

        // Act
        subestacaoMapper.updateFromRequest(request, subestacao);

        // Assert
        assertEquals("SUB02", subestacao.getCodigo());
        assertEquals("Subestação B", subestacao.getNome());
        assertEquals(new BigDecimal("-22.0"), subestacao.getLatitude());
        assertEquals(new BigDecimal("-45.0"), subestacao.getLongitude());
    }
}
