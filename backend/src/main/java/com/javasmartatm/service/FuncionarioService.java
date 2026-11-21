
package com.javasmartatm.service;

import com.javasmartatm.model.Funcionario;
import com.javasmartatm.model.enums.TipoUsuario;
import com.javasmartatm.repository.FuncionarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javasmartatm.exception.RegraNegocioException;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Funcionario cadastrarFuncionario(
            String cpf,
            String nome,
            String senha,
            TipoUsuario tipoUsuario
    ) {

        if (tipoUsuario != TipoUsuario.ATENDENTE
                && tipoUsuario != TipoUsuario.GERENTE) {
            throw new RegraNegocioException(
                    "O perfil deve ser ATENDENTE ou GERENTE."
            );
        }

        if (funcionarioRepository.existsByCpf(cpf)) {
            throw new RegraNegocioException(
                    "Já existe um funcionário cadastrado com este CPF."
            );
        }

        String senhaHash = passwordEncoder.encode(senha);

        Funcionario funcionario = new Funcionario(
                cpf,
                nome,
                senhaHash,
                tipoUsuario
        );

        return funcionarioRepository.save(funcionario);
    }

    @Transactional(readOnly = true)
    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException(
                        "Funcionário não encontrado com ID: " + id
                ));
    }

    @Transactional(readOnly = true)
    public Funcionario buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraNegocioException(
                        "Funcionário não encontrado com CPF informado."
                ));
    }


@Transactional(readOnly = true)
public org.springframework.data.domain.Page<Funcionario> listarFuncionarios(
        org.springframework.data.domain.Pageable pageable
) {
    return funcionarioRepository.findAll(pageable);
}


@Transactional
public Funcionario atualizarFuncionario(
        Long id,
        String nome
) {
    Funcionario funcionario = buscarPorId(id);

    funcionario.setNome(nome);

    return funcionarioRepository.save(funcionario);
}

@Transactional
public Funcionario alterarStatus(Long id, boolean ativo) {
    Funcionario funcionario = buscarPorId(id);

    if (ativo) {
        funcionario.ativar();
    } else {
        funcionario.desativar();
    }

    return funcionarioRepository.save(funcionario);
}

}
