
package com.javasmartatm.controller;

import com.javasmartatm.dto.request.FuncionarioRequest;
import com.javasmartatm.dto.response.FuncionarioResponse;
import com.javasmartatm.model.Funcionario;
import com.javasmartatm.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.javasmartatm.dto.request.FuncionarioAtualizacaoRequest;
import com.javasmartatm.dto.request.FuncionarioStatusRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }
    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(
            @Valid @RequestBody FuncionarioRequest request
    ) {
        Funcionario funcionario = funcionarioService.cadastrarFuncionario(
                request.cpf(),
                request.nome(),
                request.senha(),
                request.tipoUsuario()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converterParaResponse(funcionario));
    }
@PreAuthorize("hasAnyRole('ATENDENTE', 'GERENTE')")
@GetMapping
public ResponseEntity<Page<FuncionarioResponse>> listar(
        @PageableDefault(size = 10, sort = "nome")
        Pageable pageable
) {
    Page<FuncionarioResponse> funcionarios = funcionarioService
            .listarFuncionarios(pageable)
            .map(this::converterParaResponse);

    return ResponseEntity.ok(funcionarios);
}

@PreAuthorize("hasRole('GERENTE')")
@PutMapping("/{id}")
public ResponseEntity<FuncionarioResponse> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody FuncionarioAtualizacaoRequest request
) {
    Funcionario funcionario = funcionarioService.atualizarFuncionario(
            id,
            request.nome()
    );

    return ResponseEntity.ok(converterParaResponse(funcionario));
}
@PreAuthorize("hasRole('GERENTE')")
@PatchMapping("/{id}/status")
public ResponseEntity<FuncionarioResponse> alterarStatus(
        @PathVariable Long id,
        @Valid @RequestBody FuncionarioStatusRequest request
) {
    Funcionario funcionario = funcionarioService.alterarStatus(
            id,
            request.ativo()
    );

    return ResponseEntity.ok(converterParaResponse(funcionario));
}


@PreAuthorize("hasAnyRole('ATENDENTE', 'GERENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(
            @PathVariable Long id
    ) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(funcionario));
    }

    private FuncionarioResponse converterParaResponse(
            Funcionario funcionario
    ) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getCpf(),
                funcionario.getNome(),
                funcionario.getTipoUsuario(),
                funcionario.isAtivo(),
                funcionario.getCriadoEm()
        );
    }
}
