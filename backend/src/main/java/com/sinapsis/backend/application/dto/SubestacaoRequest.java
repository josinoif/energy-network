package com.sinapsis.backend.application.dto;

import java.util.List;

public record SubestacaoRequest(
    String codigo,
    String nome,
    Double latitude,
    Double longitude,
    List<RedeMTRequest> redes
) {}
