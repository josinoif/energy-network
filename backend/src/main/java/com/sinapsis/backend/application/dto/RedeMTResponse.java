package com.sinapsis.backend.application.dto;

import java.math.BigDecimal;

public record RedeMTResponse(
    Long id, 
    String codigo, 
    String nome, 
    BigDecimal tensaoNominal
) {}
