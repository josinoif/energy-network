package com.sinapsis.backend.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinapsis.backend.application.dto.RedeMTRequest;
import com.sinapsis.backend.application.dto.RedeMTResponse;
import com.sinapsis.backend.application.service.RedeMTService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/subestacoes/{idSubestacao}/redesmt")
@RequiredArgsConstructor
public class RedeMTController {

    private final RedeMTService redeMTService;

    @PostMapping
    public ResponseEntity<RedeMTResponse> criar(
            @PathVariable Long idSubestacao,
            @RequestBody @Valid RedeMTRequest request) {

        var response = redeMTService.criar(idSubestacao, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RedeMTResponse>> listar(
            @PathVariable Long idSubestacao) {

        List<RedeMTResponse> redes = redeMTService.listarPorSubestacao(idSubestacao);
        return ResponseEntity.ok(redes);
    }

    @PutMapping("/{idRede}")
    public ResponseEntity<RedeMTResponse> atualizar(
            @PathVariable Long idSubestacao,
            @PathVariable Long idRede,
            @RequestBody @Valid RedeMTRequest request) {

        RedeMTResponse response = redeMTService.atualizar(idSubestacao, idRede, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idRede}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long idSubestacao,
            @PathVariable Long idRede) {

        redeMTService.excluir(idSubestacao, idRede);
        return ResponseEntity.noContent().build();
    }

}