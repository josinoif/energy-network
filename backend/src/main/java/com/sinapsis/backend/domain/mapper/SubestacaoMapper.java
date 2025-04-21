package com.sinapsis.backend.domain.mapper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.domain.entity.Subestacao;

@Component
public class SubestacaoMapper {

    @Autowired
    private RedeMTMapper redeMTMapper;

    public void setRedeMTMapper(RedeMTMapper redeMTMapper) {
        this.redeMTMapper = redeMTMapper;
    }


    public SubestacaoResponse toResponse(Subestacao entity) {
        return new SubestacaoResponse(
            entity.getId(),
            entity.getCodigo(),
            entity.getNome(),
            entity.getLatitude().doubleValue(),
            entity.getLongitude().doubleValue(),
            entity.getRedes().stream()
                .map(redeMTMapper::toResponse)
                .toList()
        );
    }

    public Subestacao toEntity(SubestacaoRequest request) {
        Subestacao subestacao = Subestacao.builder()
            .codigo(request.codigo())
            .nome(request.nome())
            .latitude(BigDecimal.valueOf(request.latitude()))
            .longitude(BigDecimal.valueOf(request.longitude()))
            // .redes(redeMTMapper.toEntityList(request.redes()))
            .build();

        subestacao.setRedes(redeMTMapper.toEntitySet(request.redes(), subestacao));
        return subestacao;
    }


    public void updateFromRequest(SubestacaoRequest request, Subestacao entity) {
        entity.setCodigo(request.codigo());
        entity.setNome(request.nome());
        entity.setLatitude(BigDecimal.valueOf(request.latitude()));
        entity.setLongitude(BigDecimal.valueOf(request.longitude()));
        entity.setRedes(redeMTMapper.toEntitySet(request.redes(), entity));
    }
}
