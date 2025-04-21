package com.sinapsis.backend.domain.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.domain.entity.RedeMT;
import com.sinapsis.backend.domain.entity.Subestacao;

@Component
public class RedeMTMapper {

    public RedeMT toEntity(RedeMTRequest request, Subestacao subestacao) {
        return RedeMT.builder()
            .id(request.id())
            .codigo(request.codigo())
            .nome(request.nome())
            .tensaoNominal(request.tensaoNominal())
            .subestacao(subestacao)
            .build();
    }

    public RedeMTResponse toResponse(RedeMT entity) {
        return new RedeMTResponse(
            entity.getId(),
            entity.getCodigo(),
            entity.getNome(),
            entity.getTensaoNominal()
        );
    }

    public Set<RedeMT> toEntitySet(List<RedeMTRequest> requests, Subestacao subestacao) {
        return requests.stream()
            .map(request -> toEntity(request, subestacao))
            .collect(Collectors.toSet());
    }

    public List<RedeMTResponse> toResponseList(List<RedeMT> entities) {
        return entities.stream()
            .map(entity -> toResponse(entity))
            .collect(Collectors.toList());
    }
}
