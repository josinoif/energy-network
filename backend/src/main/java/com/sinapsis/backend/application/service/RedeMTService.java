package com.sinapsis.backend.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.domain.entity.RedeMT;
import com.sinapsis.backend.domain.entity.Subestacao;
import com.sinapsis.backend.domain.mapper.RedeMTMapper;
import com.sinapsis.backend.domain.repository.RedeMTRepository;
import com.sinapsis.backend.domain.repository.SubestacaoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedeMTService {

    private final RedeMTRepository redeMTRepository;
    private final SubestacaoRepository subestacaoRepository;
    private final RedeMTMapper redeMTMapper;

    public RedeMTResponse criar(Long idSubestacao, RedeMTRequest request) {
        Subestacao subestacao = subestacaoRepository.findById(idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("Subestação não encontrada"));

        RedeMT rede = redeMTMapper.toEntity(request, subestacao);
        rede = redeMTRepository.save(rede);

        return redeMTMapper.toResponse(rede);
    }

    public List<RedeMTResponse> listarPorSubestacao(Long idSubestacao) {
        subestacaoRepository.findById(idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("Subestação não encontrada"));
    
        return redeMTRepository.findAllBySubestacaoId(idSubestacao)
            .stream()
            .map(redeMTMapper::toResponse)
            .toList();
    }

    public RedeMTResponse atualizar(Long idSubestacao, Long idRede, RedeMTRequest request) {
        subestacaoRepository.findById(idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("Subestação não encontrada"));
    
        RedeMT rede = redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("RedeMT não encontrada para esta subestação"));
    
        rede.setCodigo(request.codigo());
        rede.setNome(request.nome());
        rede.setTensaoNominal(request.tensaoNominal());
    
        RedeMT atualizada = redeMTRepository.save(rede);
        return redeMTMapper.toResponse(atualizada);
    }

    public void excluir(Long idSubestacao, Long idRede) {
        subestacaoRepository.findById(idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("Subestação não encontrada"));
    
        RedeMT rede = redeMTRepository.findByIdAndSubestacaoId(idRede, idSubestacao)
            .orElseThrow(() -> new EntityNotFoundException("RedeMT não encontrada para esta subestação"));
    
        redeMTRepository.delete(rede);
    }
    
    
    
}
