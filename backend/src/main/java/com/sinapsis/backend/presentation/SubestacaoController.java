package com.sinapsis.backend.presentation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinapsis.backend.application.dto.SubestacaoRequest;
import com.sinapsis.backend.application.dto.SubestacaoResponse;
import com.sinapsis.backend.application.service.SubestacaoService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/subestacoes")
public class SubestacaoController {

    private final SubestacaoService subestacaoService;

    public SubestacaoController(SubestacaoService subestacaoService) {
        this.subestacaoService = subestacaoService;
    }

    @PostConstruct
    public void init() {
        System.out.println("SubestacaoController iniciado!");
    }

    @GetMapping
    public ResponseEntity<List<SubestacaoResponse>> listar() {
        return ResponseEntity.ok(subestacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubestacaoResponse> buscarPorId(@PathVariable Long id) {
        SubestacaoResponse response = subestacaoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        subestacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<SubestacaoResponse> criar(@Valid @RequestBody SubestacaoRequest request) {
        SubestacaoResponse response = subestacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubestacaoResponse> atualizar(@PathVariable Long id, @RequestBody SubestacaoRequest request) {
        var response = subestacaoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

}
