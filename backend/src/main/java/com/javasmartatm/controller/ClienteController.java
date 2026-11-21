
package com.javasmartatm.controller;

import com.javasmartatm.dto.request.ClienteRequest;
import com.javasmartatm.dto.request.ClienteAtualizacaoRequest;
import com.javasmartatm.dto.request.ClienteStatusRequest;
import com.javasmartatm.dto.response.ClienteResponse;
import com.javasmartatm.model.Cliente;
import com.javasmartatm.service.ClienteService;
import org.springframework.security.access.prepost.PreAuthorize;
import com.javasmartatm.security.UsuarioAutenticado;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(
            @Valid @RequestBody ClienteRequest request
    ) {

        Cliente cliente = clienteService.cadastrarCliente(
                request.cpf(),
                request.nome(),
                request.senha()
        );

        ClienteResponse response = converterParaResponse(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PreAuthorize("hasRole('CLIENTE')")
@GetMapping("/me")
public ResponseEntity<ClienteResponse> meuPerfil(
        @AuthenticationPrincipal UsuarioAutenticado usuario
) {

    Cliente cliente = clienteService.buscarPorId(usuario.getId());

    return ResponseEntity.ok(converterParaResponse(cliente));
}
@PreAuthorize("hasRole('CLIENTE')")
@PutMapping("/me")
public ResponseEntity<ClienteResponse> atualizarMeuPerfil(
        @AuthenticationPrincipal UsuarioAutenticado usuario,
        @Valid @RequestBody ClienteAtualizacaoRequest request
) {

    Cliente cliente = clienteService.atualizarCliente(
            usuario.getId(),
            request
    );

    return ResponseEntity.ok(converterParaResponse(cliente));
}

    @PreAuthorize("hasAnyRole('ATENDENTE', 'GERENTE')")
    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listar(
            @PageableDefault(size = 10, sort = "nome")
            Pageable pageable
    ) {

        Page<ClienteResponse> clientes = clienteService
                .listarClientes(pageable)
                .map(this::converterParaResponse);

        return ResponseEntity.ok(clientes);
    }

    @PreAuthorize("hasAnyRole('ATENDENTE', 'GERENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(
            @PathVariable Long id
    ) {

        Cliente cliente = clienteService.buscarPorId(id);

        return ResponseEntity.ok(converterParaResponse(cliente));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteAtualizacaoRequest request
    ) {

        Cliente cliente = clienteService.atualizarCliente(id, request);

        return ResponseEntity.ok(converterParaResponse(cliente));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResponse> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestBody ClienteStatusRequest request
    ) {

        Cliente cliente = clienteService.alterarStatus(
                id,
                request.ativo()
        );

        return ResponseEntity.ok(converterParaResponse(cliente));
    }

    private ClienteResponse converterParaResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getCpf(),
                cliente.getNome(),
                cliente.isAtivo(),
                cliente.getCriadoEm()
        );
    }
}
