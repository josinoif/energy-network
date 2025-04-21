package com.sinapsis.backend.application.dto;

import java.util.List;

public record SubestacaoResponse( 
    Long id,
    String codigo,
    String nome,
    Double latitude,
    Double longitude,
    List<RedeMTResponse> redes
) {}
