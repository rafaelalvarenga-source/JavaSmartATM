
package com.javasmartatm.service;

import com.javasmartatm.model.Cliente;
import com.javasmartatm.model.enums.TipoUsuario;
import com.javasmartatm.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javasmartatm.exception.RegraNegocioException;
import java.util.List;
import com.javasmartatm.exception.RecursoNaoEncontradoException;
import com.javasmartatm.dto.request.ClienteAtualizacaoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Cliente cadastrarCliente(
            String cpf,
            String nome,
            String senha
    ) {
        if (clienteRepository.existsByCpf(cpf)) {
            throw new RegraNegocioException(
        "Já existe um cliente cadastrado com este CPF."
);
        }

        String senhaHash = passwordEncoder.encode(senha);

        Cliente cliente = new Cliente(
        cpf,
        nome,
        senhaEncoder.encode(senha)
);

        return clienteRepository.save(cliente);
    }

@Transactional(readOnly = true)
public Page<Cliente> listarClientes(Pageable pageable) {
    return clienteRepository.findAll(pageable);
}
    @Transactional(readOnly = true)
public Cliente buscarPorId(Long id) {

    return clienteRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(
                    "Cliente não encontrado com ID: " + id
            ));
}
@Transactional
public Cliente atualizarCliente(
        Long id,
        ClienteAtualizacaoRequest request
) {

    Cliente cliente = buscarPorId(id);

    cliente.setNome(request.nome());

    return clienteRepository.save(cliente);
}

public Cliente alterarStatus(Long id, boolean ativo) {

    Cliente cliente = buscarPorId(id);

    if (ativo) {
        cliente.ativar();
    } else {
        cliente.desativar();
    }

    return clienteRepository.save(cliente);
}

}
