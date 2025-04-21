package com.sinapsis.backend.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.domain.entity.RedeMT;
import com.sinapsis.backend.domain.entity.Subestacao;

class RedeMTMapperTest {

    private RedeMTMapper redeMTMapper;

    @BeforeEach
    void setUp() {
        redeMTMapper = new RedeMTMapper();
    }

    @Test
    void toEntity_DeveRetornarRedeMT_QuandoDadosValidos() {
        // Arrange
        RedeMTRequest request = new RedeMTRequest("MT01", "Rede Teste", new BigDecimal("13.8"));
        Subestacao subestacao = Subestacao.builder()
            .id(1L)
            .nome("Subestação A")
            .build();

        // Act
        RedeMT result = redeMTMapper.toEntity(request, subestacao);

        // Assert
        assertNotNull(result);
        assertEquals("MT01", result.getCodigo());
        assertEquals("Rede Teste", result.getNome());
        assertEquals(new BigDecimal("13.8"), result.getTensaoNominal());
        assertEquals(subestacao, result.getSubestacao());
    }

    @Test
    void toResponse_DeveRetornarRedeMTResponse_QuandoDadosValidos() {
        // Arrange
        RedeMT redeMT = RedeMT.builder()
            .id(1L)
            .codigo("MT01")
            .nome("Rede Teste")
            .tensaoNominal(new BigDecimal("13.8"))
            .build();

        // Act
        RedeMTResponse result = redeMTMapper.toResponse(redeMT);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("MT01", result.codigo());
        assertEquals("Rede Teste", result.nome());
        assertEquals(new BigDecimal("13.8"), result.tensaoNominal());
    }
}
