package com.sinapsis.backend.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.domain.entity.Subestacao;
import com.sinapsis.backend.domain.mapper.SubestacaoMapper;
import com.sinapsis.backend.domain.repository.SubestacaoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubestacaoService {

    private final SubestacaoRepository subestacaoRepository;

    private final SubestacaoMapper subestacaoMapper;

    public SubestacaoService(SubestacaoRepository subestacaoRepository, SubestacaoMapper subestacaoMapper) {
        this.subestacaoRepository = subestacaoRepository;
        this.subestacaoMapper = subestacaoMapper;
    }

    public List<SubestacaoResponse> listarTodos() {
        return subestacaoRepository.findAll()
                .stream()
                .map(subestacaoMapper::toResponse)
                .toList();
    }

    public SubestacaoResponse buscarPorId(Long id) {
        Subestacao subestacao = subestacaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Subestação com id=" + id + " não encontrada"));
        
        return subestacaoMapper.toResponse(subestacao);
    }

    public void excluir(Long id) {

        if (!subestacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Subestação com id=" + id + " não encontrada");
        }

        subestacaoRepository.deleteById(id);
    }

    public SubestacaoResponse criar(SubestacaoRequest request) {
        Subestacao subestacao = subestacaoMapper.toEntity(request);
        Subestacao salva = subestacaoRepository.save(subestacao);
        return subestacaoMapper.toResponse(salva);
    }

    public SubestacaoResponse atualizar(Long id, SubestacaoRequest request) {
        var subestacao = subestacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subestação não encontrada"));

        subestacaoMapper.updateFromRequest(request, subestacao);

        var atualizada = subestacaoRepository.save(subestacao);
        return subestacaoMapper.toResponse(atualizada);
    }

}