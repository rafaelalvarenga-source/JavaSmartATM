
package com.javasmartatm.security;

import com.javasmartatm.model.Cliente;
import com.javasmartatm.model.Funcionario;
import com.javasmartatm.model.enums.TipoUsuario;
import com.javasmartatm.repository.ClienteRepository;
import com.javasmartatm.repository.FuncionarioRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public UsuarioDetailsService(
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf)
            throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.findByCpf(cpf).orElse(null);

        if (cliente != null) {
            return new UsuarioAutenticado(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getSenhaHash(),
                    TipoUsuario.CLIENTE,
                    cliente.isAtivo()
            );
        }

        Funcionario funcionario = funcionarioRepository
                .findByCpf(cpf)
                .orElse(null);

        if (funcionario != null) {
            return new UsuarioAutenticado(
                    funcionario.getId(),
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getSenhaHash(),
                    funcionario.getTipoUsuario(),
                    funcionario.isAtivo()
            );
        }

        throw new UsernameNotFoundException(
                "Usuário não encontrado."
        );
    }
}
